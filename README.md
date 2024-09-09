# BillProduct-backend-Project-
Modules:

    1. Product.
    2. Product Charges
    3. Bill

•	Product Endpoints:

1. POST/api/addproduct : Add new Product
2. GET/api/getAllProduct : Get all the product list.
3. GET/api/getproductBy/{id} : Get the specific product by using ID.
4. PUT/api/update-product/{id} : Update the existing product.
5. DELETE/api/deleteProductById/{id} : Delete the product through ID.


•	Product Charges Endpoints: 

1. POST/api/add-productCharges : Add product charges details.
2. GET/api/getAllCategory : Get all products categories with other details.
3. GET/api/getchargesBycategory/{name} : Get the charges applied on the specific category by name.
4. DELETE/api/delete-category/{categoryName} : Delete category through category name.


•	Bill Endpoints:

1. GET/api/getBill/{id} : Get the bill through Prosuct Id.
2. GET/api/getBillByName/{productName} : Get the Bill through product name.
3. GET/api/getBillBycategoryName/{categoryName} : Get the Bill through Product Category.



The Output Of the bill Endpoints will be as follows.

    {
    "productId": 1001,
    "name": "Lenovo Yoga",
    "productType": "Laptop",
    "category": "Electronics",
    "baseprice": 45000.0,
    "discount": 6750.0,
    "finalPrice": 45485.0,
    "charges": {
    "gst": 6885.0,
    "deliveryCherges": 350.0
     }
    }

In this project there is only two entity classes i.e. product and Product charges, the rest operation of calculating bill is done with help of classes which acts as a utility classes.

Following are the factors that determined dynamically.
1. GST.
2. discount.
3. finalPrice.
4. Delivery Charges.

