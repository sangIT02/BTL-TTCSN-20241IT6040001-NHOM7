package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ISuppliersService suppliersService;

    @Autowired
    private IOrderDetailService orderDetailService;
    //test
    @GetMapping(value = "/api/product/")
    public List<ProductDTO> ShowProduct(){
        List<ProductDTO> result = productService.findAllProducts();
        return result;
    }
    //test
    @GetMapping(value = "/api/product/{productName}/{brand}")
    public List<ProductDTO> SearchProductByNameAndBrand(@PathVariable String productName, @PathVariable String brand){
        List<ProductDTO> result = productService.findByNameContainingAndBrandContainingAtService(productName,brand);
        return result;
    }
    //test
    @GetMapping(value = "/api/search/{productName}/{supplierName}")
    public List<ProductAndSupplierDTO> SearchProductByNameProductAndNameSupplier(@PathVariable String productName, @PathVariable String supplierName){
        List<ProductAndSupplierDTO> result = productService.findByProductNameAndSupplier_SupplierNameAtService(productName,supplierName);
        return result;
    }

    //API lấy tổng tiền trong tháng hiện tại
    @GetMapping(value = "/admin/totalmount/")
    public Order_TotalAmountInMonthDTO TotalAmountInMount(){
        Order_TotalAmountInMonthDTO res = orderService.getTotalAmountInMountAtService();
        return res;
    }


    //API lấy tổng khách hàng trong tháng hiện tại
    @GetMapping(value = "/admin/totalcustomer/")
    public Integer TotalCustomerInMonth(){
        Integer result = orderService.TotalCustomerInMonthAtService();
        return result;
    }

    //API lấy số khách hàng mới trong thời điểm nhất định
//    @GetMapping(value = "/admin/countcustomer/")
//    public Integer CountNewCustomer(@RequestParam Map<String,Object> params){
//        Integer result = customerService.countCustomers(params);
//        return result;
//    }


   //API lấy số khách mới mỗi tháng cho biểu đồ
    @GetMapping(value = "/admin/countcustomer/")
    public List<Customer_CountNewCustomerPerMonthDTO> CountCustomerInMonth(){
        List<Customer_CountNewCustomerPerMonthDTO> result = customerService.listCountNewCustomerPerMonth();
        return result;
    }


    //API lấy số sp bán được trong các tháng cho biểu đồ
    @GetMapping(value = "/admin/quantitysell/")
    public List<OrderDetail_CountQuantityProductPerMonthDTO> QuantitySellProduct(){
        List<OrderDetail_CountQuantityProductPerMonthDTO> result = orderDetailService.listCountQuantityProductPerMonth();
        return result;
    }

    //API lấy doanh thu trong các tháng
    @GetMapping(value = "/admin/totalamount/")
    public List<Order_CountTotalAmountDTO> TotalAmount(){
        List<Order_CountTotalAmountDTO> result = orderService.listCountTotalAmountAtService();
        return result;
    }

    //API lấy top sản phẩm
    @GetMapping(value = "/admin/topproduct/")
    public List<Product_FindTopPurchasedProductsDTO> TopProduct(){
        List<Product_FindTopPurchasedProductsDTO> result = productService.findTopPurchasedProductAtService();
        return result;
    }

    //API lấy top nhà cung cấp
    @GetMapping(value = "/admin/topsuppliers/")
    public List<SuppliersDTO> TopSuppliers(){
        List<SuppliersDTO> result = suppliersService.listTopSupplier();
        return result;
    }
    //API tạo sản phẩm
    @PostMapping("/admin/createproduct/")
    public void createProduct(@RequestBody Product_CreateProductDTO createProductDTO){
        productService.createNewProduct(createProductDTO);
    }
    //API tạo nhà cung cấp
    @PostMapping("/admin/createsupplier/")
    public void createSupplier(@RequestBody Supplier_CreateSupplierDTO createSupplierDTO){
        suppliersService.createSupplier(createSupplierDTO);
    }

    //API cập nhật  sản phẩm (trả về cho repo product chứa id)
    @PutMapping("/admin/updateproduct/")
    public void updateProduct(@RequestBody Product_UpdateProductDTO updateProductDTO){
        productService.updateProduct(updateProductDTO);
    }

    //API xóa khách hàng ( thay đổi status)
    @DeleteMapping("/admin/deletecustomer/{ids}")
    public void deleteCustomer(@PathVariable Long[] ids){
        customerService.deleteCustomerAtService(ids);
    }

    //API lấy danh sách bill
    @GetMapping(value = "/admin/listbill/")
    public List<Order_ListBillDTO> ListBill(){
        List<Order_ListBillDTO> result = orderService.ListBillAtService();
        return result;
    }

    //API lấy danh sách invoice chi tiết
    @GetMapping(value = "/admin/list_invoicedetail/")
    public List<Order_InvoiceDetailDTO> ListInvoice(){
        List<Order_InvoiceDetailDTO> result = orderService.ListInvoiceDetailAtService();
        return result;
    }

//ádasdasdqƯDAWDADSASD

}