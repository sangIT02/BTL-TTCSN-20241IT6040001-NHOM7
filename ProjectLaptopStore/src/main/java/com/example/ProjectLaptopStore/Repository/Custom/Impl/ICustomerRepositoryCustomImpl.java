package com.example.ProjectLaptopStore.Repository.Custom.Impl;

import com.example.ProjectLaptopStore.Builder.CustomerSearchBuilder;
import com.example.ProjectLaptopStore.DTO.Customer_CountNewCustomerPerMonthDTO;
import com.example.ProjectLaptopStore.DTO.Customer_CreateCustomerDTO;
import com.example.ProjectLaptopStore.DTO.Customer_FindTopCustomer;
import com.example.ProjectLaptopStore.DTO.Customer_UpdateCustomerDTO;
import com.example.ProjectLaptopStore.Entity.CustomerEntity;
import com.example.ProjectLaptopStore.Entity.Enum.Customer_Enum;
import com.example.ProjectLaptopStore.Entity.Enum.User_Enum;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.example.ProjectLaptopStore.Repository.Custom.ICustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class ICustomerRepositoryCustomImpl implements ICustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    // Hàm xây dựng câu truy vấn điều kiện
    public static void queryNomal(CustomerSearchBuilder customerSearchBuilder, StringBuilder where) {
        // Nếu có ngày bắt đầu
        if (customerSearchBuilder.getDateRegisterFrom() != null) {
            where.append(" and c.RegistrationDate >= :dateRegisterFrom ");
        }
        // Nếu có ngày kết thúc
        if (customerSearchBuilder.getDateRegisterTo() != null) {
            where.append(" and c.RegistrationDate <= :dateRegisterTo ");
        }
    }

//    @Override
//    public Integer countNewCustomer(CustomerSearchBuilder customerSearchBuilder) {
//        StringBuilder sql = new StringBuilder("select count(distinct c.CustomerID) from customers c ");
//        StringBuilder where = new StringBuilder(" where 1=1 ");
//        queryNomal(customerSearchBuilder, where);
//        sql.append(where);
//        // Hiển thị câu lệnh trong console (nếu cần kiểm tra)
//        System.out.println(sql.toString());
//
//        Query query = entityManager.createNativeQuery(sql.toString());
//        // Set các tham số cho câu truy vấn
//        if (customerSearchBuilder.getDateRegisterFrom() != null) {
//            query.setParameter("dateRegisterFrom", customerSearchBuilder.getDateRegisterFrom());
//        }
//        if (customerSearchBuilder.getDateRegisterTo() != null) {
//            query.setParameter("dateRegisterTo", customerSearchBuilder.getDateRegisterTo());
//        }
//        // Trả về kết quả duy nhất
//        try {
//            return ((Number) query.getSingleResult()).intValue();
//        } catch (NoResultException e) {
//            return 0;
//        }
//    }

    @Override
    public List<Customer_CountNewCustomerPerMonthDTO> listNewCustomerPerMonth() {
        String query = "SELECT " +
                "MONTH(RegistrationDate) AS month, " +
                "COUNT(DISTINCT CustomerID) AS NewCustomerCount " +
                "FROM " +
                "Customers " +
                "WHERE YEAR(RegistrationDate) = YEAR(CURDATE()) " +
                "GROUP BY Month " +
                "ORDER BY Month ";
        Query nativeQuery = entityManager.createNativeQuery(query) ;
        List<Object[]> resultQuery = nativeQuery.getResultList();
        List<Customer_CountNewCustomerPerMonthDTO> listCountNewCustomer = new ArrayList<>();
        for(Object[] rowOfResult : resultQuery) {
            Customer_CountNewCustomerPerMonthDTO dto = new Customer_CountNewCustomerPerMonthDTO(
                    (Long) rowOfResult[0],
                    (Long) rowOfResult[1]
            );
            listCountNewCustomer.add(dto);
        }
        return listCountNewCustomer;
    }

    @Override
    public void deleteCustomer(List<CustomerEntity> listCustomerDelete) {
        for(CustomerEntity cusOfListDel : listCustomerDelete) {
            cusOfListDel.setStatus(Customer_Enum.suspended);
            entityManager.merge(cusOfListDel);
        }
        entityManager.flush();
    }

    @Override
    public void createCustomer(Customer_CreateCustomerDTO customerCreate) {
        CustomerEntity customerEntity = new CustomerEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(customerCreate.getFullName());
        userEntity.setEmail(customerCreate.getEmail());
        userEntity.setPassword(customerCreate.getPassWord());
        userEntity.setPhoneNumber(customerCreate.getPhoneNumber());
        userEntity.setUserType(User_Enum.customer);
        userEntity.setRegistrationDate(customerCreate.getRegistrationDate());
        customerEntity.setUser(userEntity);
        customerEntity.setAddress(customerCreate.getAddress());
        customerEntity.setCity(customerCreate.getCity());
        customerEntity.setDistrict(customerCreate.getDistrict());
        customerEntity.setWard(customerCreate.getWard());
        customerEntity.setStreetAddress(customerCreate.getStreetAddress());
        customerEntity.setRegistrationDate(customerCreate.getRegistrationDate());
        customerEntity.setStatus(Customer_Enum.active);
        entityManager.persist(userEntity);
        entityManager.persist(customerEntity);
    }

    @Override
    public void updateCustomer(Customer_UpdateCustomerDTO customerUpdate, CustomerEntity customerEntity, UserEntity userEntity) {
        userEntity.setFullName(customerUpdate.getFullName());
        userEntity.setEmail(customerUpdate.getEmail());
        userEntity.setPassword(customerUpdate.getPassWord());
        userEntity.setPhoneNumber(customerUpdate.getPhoneNumber());
        userEntity.setUserType(User_Enum.customer);
        userEntity.setRegistrationDate(customerUpdate.getRegistrationDate());
        customerEntity.setUser(userEntity);
        customerEntity.setAddress(customerUpdate.getAddress());
        customerEntity.setCity(customerUpdate.getCity());
        customerEntity.setDistrict(customerUpdate.getDistrict());
        customerEntity.setWard(customerUpdate.getWard());
        customerEntity.setStreetAddress(customerUpdate.getStreetAddress());
        customerEntity.setRegistrationDate(customerUpdate.getRegistrationDate());
        customerEntity.setStatus(Customer_Enum.active);
        entityManager.merge(userEntity);
        entityManager.merge(customerEntity);
        entityManager.flush();
    }


//    @Override
//    public List<Customer_FindTopCustomer> findTopCustomer() {
//        String query = "SELECT " +
//                "o.OrderDate, " +
//                "u.FullName, " +
//                "FROM Customers c " +
//                "JOIN Users u ON u.UserID = c.UserID " +
//                "JOIN Orders o ON o.CustomerID = c.CustomerID " +
//                "ORDER BY o.OrderDate DESC";
//        return null;
//    }
}
