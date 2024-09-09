package com.tka.BillProduct.controller;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tka.BillProduct.entity.Bill;
import com.tka.BillProduct.entity.Charges;
import com.tka.BillProduct.entity.Product;
import com.tka.BillProduct.entity.ProductCharges;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	SessionFactory sessionfactory;
	Session session;
	Transaction transaction;
	Product products;
	List<Product> query;
	String returnMsg="Sorry we didn't have the product you have asked";
	
	

	@PostMapping("/addproduct")
	public void addProduct(@RequestBody Product product) {

		try {
			Session session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			session.save(product);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}
		}

	}

	@GetMapping("/getAllProduct")
	public List<Product> getAllProduct() {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			query = session.createQuery("FROM Product", Product.class).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}
		}
		return query;

	}

	@GetMapping("/getproductBy/{id}")
	public Product getProduct(@PathVariable int id) {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			products = session.get(Product.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}

		}
		return products;

	}

	@PutMapping("/update-product/{id}")
	public String updateProduct(@PathVariable int id, @RequestBody Product productDatails) {
		try {

			Product temp = getProduct(id);
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			if (temp != null) {
				temp.setProductId(productDatails.getProductId());
				temp.setProductCategory(productDatails.getProductCategory());
				temp.setProductName(productDatails.getProductName());
				temp.setProductType(productDatails.getProductType());
				temp.setProductPrice(productDatails.getProductPrice());

				session.saveOrUpdate(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			transaction.commit();
			if (session != null) {
				session.close();
			}
		}

		return "Update successfully..!";
	}

	@DeleteMapping("/deleteProductById/{id}")
	public String deleteById(@PathVariable int id) {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			Product temp = session.get(Product.class, id);
			if (temp != null) {
//			System.out.println("Hiii i am ishwar vaidya");
				session.delete(temp);
			} else {
				return "The object is null";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			transaction.commit();
			if (session != null) {
				session.close();
			}
		}
		return "Deleted Successfully..!";
	}

	@PostMapping("/add-ProductCharges")
	public void addProductCharges(@RequestBody ProductCharges chargesDetails) {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			session.save(chargesDetails);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}
		}

	}

	@GetMapping("/getAllCategory")
	public List<ProductCharges> getAllcategory() {
		List<ProductCharges> prdcharge = null;
		try {

			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			prdcharge = session.createQuery("FROM ProductCharges", ProductCharges.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}

		}

		return prdcharge;
	}

	@GetMapping("/getChargesByCategory/{name}")
	public List getProductChargesByCategory(@PathVariable String name) {
		List prdcharges = null;
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(ProductCharges.class);
			criteria.add(Restrictions.eq("productCatgory", name));
			prdcharges = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}
		}

		return prdcharges;

	}

	@DeleteMapping("/delete-category/{categoryName}")
	public void deleteCategory(@PathVariable String categoryName) {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			ProductCharges prdObject = session.get(ProductCharges.class, categoryName);
			if (prdObject != null) {
				session.delete(prdObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				transaction.commit();
				session.close();
			}
		}

	}

	@GetMapping("/getBill/{productId}")
	public Object getProductBill(@PathVariable int productId) {

		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			Product product = session.get(Product.class, productId);

			if (product != null) {
				Bill bill = new Bill();
				bill.setProductId(product.getProductId());
				bill.setName(product.getProductName());
				bill.setCategory(product.getProductCategory());
				bill.setProductType(product.getProductType());
				bill.setBaseprice(product.getProductPrice());

				// To get discount amount , gst , delivery charges
				String categoury = product.getProductCategory();
				System.out.println(categoury);
				ProductCharges prdCharges = session.get(ProductCharges.class, categoury);
				System.out.println(prdCharges);
			

				// calculate discount amount
				double basePrice = product.getProductPrice();
				double discountAmount = ((basePrice * prdCharges.getDiscount()) / 100);

				bill.setDiscount(discountAmount);

				// Delivery charges and GST
				Charges charges = new Charges();
				charges.setDeliveryCherges(prdCharges.getDeliveryCharge());

				// GST will be applied on amount excluding discount
				double gstPercentage = prdCharges.getgST();
				double AmountToPay = basePrice - discountAmount;
				double gstAmount = (AmountToPay * gstPercentage) / 100;

				charges.setGST(gstAmount);

				bill.setCharges(charges);

				double finalprice = basePrice - discountAmount + (prdCharges.getDeliveryCharge() + gstAmount);
				

				bill.setFinalPrice(finalprice);
				return bill;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session !=null) {
				transaction.commit();
				session.close();
			}
		}

	return returnMsg;

	}
	
	@GetMapping("/getBillbyName/{productName}")
	public Object getBillByname(@PathVariable String productName) {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			List<Product> list = criteria.list();
			
			Product product =null
					;
			
			for (Product p : list) {
				if(p.getProductName().equals(productName)) {
					product = p;
				}
				
			}
			System.out.println(product);

			if (product != null) {
				Bill bill = new Bill();
				bill.setProductId(product.getProductId());
				bill.setName(product.getProductName());
				bill.setCategory(product.getProductCategory());
				bill.setProductType(product.getProductType());
				bill.setBaseprice(product.getProductPrice());

				// To get discount amount , gst , delivery charges
				String categoury = product.getProductCategory();
				System.out.println(categoury);
				ProductCharges prdCharges = session.get(ProductCharges.class, categoury);
				System.out.println(prdCharges);
			

				// calculate discount amount
				double basePrice = product.getProductPrice();
				double discountAmount = ((basePrice * prdCharges.getDiscount()) / 100);

				bill.setDiscount(discountAmount);

				// Delivery charges and GST
				Charges charges = new Charges();
				charges.setDeliveryCherges(prdCharges.getDeliveryCharge());

				// GST will be applied on amount excluding discount
				double gstPercentage = prdCharges.getgST();
				double AmountToPay = basePrice - discountAmount;
				double gstAmount = (AmountToPay * gstPercentage) / 100;

				charges.setGST(gstAmount);

				bill.setCharges(charges);

				double finalprice = basePrice - discountAmount + (prdCharges.getDeliveryCharge() + gstAmount);
				

				bill.setFinalPrice(finalprice);
				return bill;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session !=null) {
				transaction.commit();
				session.close();
			}
		}

	return returnMsg;
	}
	
	@GetMapping("/getBillBycategoryName/{categoryName}")
	public Object getBillBycategory(@PathVariable String categoryName) {
		try {
			session = sessionfactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			List<Product> list = criteria.list();
			
			Product product =null
					;
			
			for (Product p : list) {
				if(p.getProductCategory().equals(categoryName)) {
					product = p;
				}
				
			}
			System.out.println(product);

			if (product != null) {
				Bill bill = new Bill();
				bill.setProductId(product.getProductId());
				bill.setName(product.getProductName());
				bill.setCategory(product.getProductCategory());
				bill.setProductType(product.getProductType());
				bill.setBaseprice(product.getProductPrice());

				// To get discount amount , gst , delivery charges
				String categoury = product.getProductCategory();
				System.out.println(categoury);
				ProductCharges prdCharges = session.get(ProductCharges.class, categoury);
				System.out.println(prdCharges);
			

				// calculate discount amount
				double basePrice = product.getProductPrice();
				double discountAmount = ((basePrice * prdCharges.getDiscount()) / 100);

				bill.setDiscount(discountAmount);

				// Delivery charges and GST
				Charges charges = new Charges();
				charges.setDeliveryCherges(prdCharges.getDeliveryCharge());

				// GST will be applied on amount excluding discount
				double gstPercentage = prdCharges.getgST();
				double AmountToPay = basePrice - discountAmount;
				double gstAmount = (AmountToPay * gstPercentage) / 100;

				charges.setGST(gstAmount);

				bill.setCharges(charges);

				double finalprice = basePrice - discountAmount + (prdCharges.getDeliveryCharge() + gstAmount);
				

				bill.setFinalPrice(finalprice);
				return bill;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session !=null) {
				transaction.commit();
				session.close();
			}
		}

	return returnMsg;
	}
	
	
	
	
	
	
	
}
