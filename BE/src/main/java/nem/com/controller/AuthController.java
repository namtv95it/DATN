package nem.com.controller;

import nem.com.domain.request.ChangePasswordDTO;
import nem.com.domain.request.LoginForm;
import nem.com.domain.request.RegisterFormUser;
import nem.com.domain.response.JwtResponse;
import nem.com.entity.Customers;
import nem.com.entity.Employees;
import nem.com.exception.LoginInvalidException;
import nem.com.exception.UserInactiveException;
import nem.com.security.jwt.JwtProvider;
import nem.com.service.CustomerService;
import nem.com.service.EmailService;
import nem.com.service.EmployeeService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Qualifier("authenticationManagerUser")
    @Autowired
    private AuthenticationManager authenticationManagerUser;

    @Qualifier("authenticationManagerAdmin")
    @Autowired
    private AuthenticationManager authenticationManagerAdmin;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @PostMapping("user/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginForm request) {
        Customers customer = this.customerService.findCustomerByEmail(request.getEmail());
        if (customer.getStatus() == 0 && this.passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new UserInactiveException("Tài khoản đã bị vô hiệu hoá !");
        }
        try {
            Authentication authentication = authenticationManagerUser.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.createTokenCustomer(authentication);

            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new LoginInvalidException("Sai thông tin tài khoản hoặc mật khẩu !");
        }
    }

    @PostMapping("admin/login")
    public ResponseEntity<JwtResponse> loginAdmin(@RequestBody LoginForm request) {
        Employees employee = this.employeeService.findEmployeeByEmail(request.getEmail());
        if (employee.getStatus() == 0 && this.passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new UserInactiveException("Tài khoản đã bị vô hiệu hoá !");
        }
        try {
            Authentication authentication = authenticationManagerAdmin.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.createTokenEmployee(authentication);

            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new LoginInvalidException("Sai thông tin tài khoản hoặc mật khẩu !");
        }
    }

    @PostMapping("user/register")
    public ResponseEntity<Customers> registerUser(@RequestBody RegisterFormUser request) {
        Customers customer = this.mapper.map(request, Customers.class);
        customer.setStatus((short) 1);
        return new ResponseEntity<>(this.customerService.save(customer), HttpStatus.OK);
    }

    @GetMapping("user/send-email")
    public ResponseEntity<Boolean> forgotPassword(@RequestParam("email") String email, @RequestParam("url") String url) {
        String token = RandomString.make(45);
        this.customerService.updateResetPassword(email, token);
        String resetPasswordLink = url + "/forgot-password?token=" + token;
        return new ResponseEntity<>(emailService.sendSimpleMail(email, resetPasswordLink), HttpStatus.OK);
    }

    @GetMapping("user/get-password-token")
    public ResponseEntity<Customers> getCustomerByPasswordToken(@RequestParam("password-token") String passwordToken) {
        return new ResponseEntity<>(this.customerService.findByResetPasswordToken(passwordToken), HttpStatus.OK);
    }

    @PostMapping("user/change-password")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDTO request) {
        return new ResponseEntity<>(this.customerService.updatePassword(
                request.getCustomerId(),
                this.passwordEncoder.encode(request.getNewPassword())),
                HttpStatus.OK);
    }
}
