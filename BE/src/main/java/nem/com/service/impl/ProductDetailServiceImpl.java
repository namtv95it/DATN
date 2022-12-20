package nem.com.service.impl;

import lombok.AllArgsConstructor;
import nem.com.domain.request.ProductDetailsDTO;
import nem.com.domain.request.ServiceResult;
import nem.com.domain.response.ProductDetailResponseDTO;
import nem.com.entity.Colors;
import nem.com.entity.ProductsDetails;
import nem.com.entity.Sizes;
import nem.com.exception.ResourceNotFoundException;
import nem.com.exception.UniqueFieldException;
import nem.com.repository.ColorsRepository;
import nem.com.repository.ProductsDetailsRepository;
import nem.com.repository.SizesRepository;
import nem.com.service.ProductDetailService;
import nem.com.utils.BarcodeUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductsDetailsRepository productsDetailsRepository;
    private ColorsRepository colorsRepository;
    private SizesRepository sizesRepository;

    @Override
    public List<ProductsDetails> getAll() {
        return this.productsDetailsRepository.findAll();
    }

    @Override
    public ByteArrayInputStream dowloadExcel() throws IOException{
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet");
            Row row0 = sheet.createRow(0);

            String[] arr = new String[]{"colorId","sizeId","quantity"};
            for (int i = 0; i < arr.length; i++) {
                Cell cellA1 = row0.createCell(i);
                cellA1.setCellValue(arr[i]);
            }

            //Get list id name color
            List<Colors> colorsList = this.colorsRepository.findAll();
            List<String> stringListColor = colorsList.stream().map(res-> res.getId()+" - "+res.getName()).collect(Collectors.toList());

            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1,256,0,0);
            DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(stringListColor.toArray(new String[0]));

            HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList,dvConstraint);
            dataValidation.setSuppressDropDownArrow(false);
            sheet.addValidationData(dataValidation);

            //Get list id name size
            List<Sizes> sizesList = this.sizesRepository.findAll();
            List<String> stringListSize = sizesList.stream().map(res-> res.getId()+" - "+res.getCode()).collect(Collectors.toList());

            CellRangeAddressList cellRangeAddressList2 = new CellRangeAddressList(1,256,1,1);
            DVConstraint dvConstraint2 = DVConstraint.createExplicitListConstraint(stringListSize.toArray(new String[0]));

            HSSFDataValidation dataValidation2 = new HSSFDataValidation(cellRangeAddressList2,dvConstraint2);
            dataValidation.setSuppressDropDownArrow(false);
            sheet.addValidationData(dataValidation2);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            workbook.close();

            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Override
    public ProductsDetails getOne(Integer id) {
        return this.productsDetailsRepository.findById(id).get();
    }

    @Override
    public ProductsDetails update(ProductsDetails productsDetails) {
        return this.productsDetailsRepository.save(productsDetails);
    }

    @Override
    public void delete(Integer id) {
        ProductsDetails productsDetails = getOne(id);
        this.productsDetailsRepository.delete(productsDetails);
    }

    @Override
    public ProductsDetails save(ProductsDetails productsDetails) {
        return this.productsDetailsRepository.save(productsDetails);
    }

    @Override
    public List<ProductDetailsDTO> getByIdProduct(Integer id) {
        return this.productsDetailsRepository.getProductDetailsByIdPro(id);
    }
    public List<ProductsDetails> findProductsDetailsByProductId(Integer productId) {
        return this.productsDetailsRepository.findProductsDetailsByProductId(productId);
    }

    @Override
    public ProductsDetails findProductDetailBySizeAndColor(Integer productId, Integer sizeId, Integer colorId) {
        return this.productsDetailsRepository.findProductDetailBySizeAndColor(productId, sizeId, colorId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductDetailResponseDTO> createProductDetails(List<ProductDetailResponseDTO> list) {
        for (ProductDetailResponseDTO p: list
        ) {
            try {
                ProductsDetails productsDetails = new ProductsDetails();

            int productId = p.getProduct().getId();
            int colorId = p.getColor().getId();
            int sizeId = p.getSize().getId();
            List<ProductsDetails> productsDetailsList = this.productsDetailsRepository.findProductDetailByProductSizeColor(productId,colorId,sizeId);
            if (!productsDetailsList.isEmpty()){
                productsDetails = this.productsDetailsRepository.findProductDetailByProductSizeColor(productId,colorId,sizeId).get(0);
                productsDetails.setQuantity(productsDetails.getQuantity()+p.getQuantity());
            }else{
                productsDetails.setProduct(p.getProduct());
                productsDetails.setColor(p.getColor());
                productsDetails.setSize(p.getSize());
                productsDetails.setQuantity(p.getQuantity());
                while(true){
                    Random generator = new Random();
                    int barCode = generator.nextInt((9999999 - 1000000) + 1) + 1000000;
                    ProductsDetails productsDetails1 = this.productsDetailsRepository.getByBarCode(barCode+"");
                    System.out.println(productsDetails1);
                    if (productsDetails1 == null){
                        productsDetails.setBarCode(barCode+"");
                        break;
                    }
                }
            }
                this.productsDetailsRepository.save(productsDetails);
            }catch (Exception e){
                throw new UniqueFieldException("ABC");
            }
        }

        return list;
    }

    @Override
    public List<ProductsDetails> findProductDetailByProductSizeColor(ProductDetailResponseDTO productViewDto) {
        int productId = productViewDto.getProduct().getId();
        int colorId = productViewDto.getColor().getId();
        int sizeId = productViewDto.getSize().getId();
        return this.productsDetailsRepository.findProductDetailByProductSizeColor(productId,colorId,sizeId);
    }

    @Override
    public ProductsDetails getByBarcode(String barcode){
        return this.productsDetailsRepository.getByBarCode(barcode);
    }

    @Override
    public ServiceResult<?> generateBarcode(Integer id){
        ProductsDetails productsDetails = productsDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        String barCode = productsDetails.getBarCode();
        ServiceResult<?> result = new ServiceResult<>();
       result.setMessage(BarcodeUtils.generateBarcode(barCode,productsDetails.getProduct().getPrice(),300,100));
        return result;
    }
}
