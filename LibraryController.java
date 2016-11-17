package com.ustri.LibraryManagement.controller.webService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ustri.LibraryManagement.customException.LibraryManagementCustomException;
import com.ustri.LibraryManagement.model.Pojo.Book;
import com.ustri.LibraryManagement.model.Pojo.Ebook;
import com.ustri.LibraryManagement.model.Pojo.User;
import com.ustri.LibraryManagement.model.ServiceLayer.LibraryManagementInterface;


@Path("/mypath")
@Component
public class LibraryController {
	
	@Autowired
	LibraryManagementInterface libManageInerface;

	public LibraryManagementInterface getLibManageInerface() {
		return libManageInerface;
	}

	public void setLibManageInerface(LibraryManagementInterface libManageInerface) {
		this.libManageInerface = libManageInerface;
	}
	
	
	@POST
	@Path("/signUp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public String addUserDetails(JSONObject signUpdataObj)throws LibraryManagementCustomException, JSONException, ParseException{
	String firstName=signUpdataObj.getString("fName");
	String lastName=signUpdataObj.getString("lName");
	String gender=signUpdataObj.getString("gender");
	String membershipType=signUpdataObj.getString("opt");
	String address=signUpdataObj.getString("address");
	Long phoneNo=signUpdataObj.getLong("phoneNo");
	String email=signUpdataObj.getString("email");
	String userName=signUpdataObj.getString("uName");
	String pass=signUpdataObj.getString("pword");
	System.out.println(membershipType);
	User u=new User();
	u.setUserName(userName);
	u.setAddress(address);
	u.setEmailId(email);
	u.setGender(gender);
	u.setMembershipType(membershipType);
	u.setName(firstName+" "+lastName);
	u.setPassword(pass);
	u.setPhoneNo(phoneNo);
	return libManageInerface.addUserDetails(u);
	}
	
	@POST
	@Path("/Login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public String authenticateUser(JSONObject logindataObj)
										throws LibraryManagementCustomException, JSONException{
		String userName=logindataObj.getString("username");
		String password=logindataObj.getString("password");
		User u=new User();
		u.setUserName(userName);
		u.setPassword(password);
		return libManageInerface.authenticateUser(u);
	
		
	}
	
	//*************************EBOOK*********************////
	
	
	@POST
	@Path("/insertNewEBook")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertEBooks(JSONObject ebookUpload) throws Exception {
		// TODO Auto-generated method stub
		String ebookId=ebookUpload.getString("ebookId");
		String ebookName=ebookUpload.getString("ebookName");
		String ebookLink=ebookUpload.getString("ebookLink");
		Ebook book=new Ebook();
		book.seteBookId(ebookId);
		book.seteBookName(ebookName);
		FileInputStream fs = new FileInputStream(ebookLink);
	    String ebookLinkTarget="D:\\eBooks\\"+ebookId+".pdf";
	    FileOutputStream os = new FileOutputStream(ebookLinkTarget);
	    int b;
	    while ((b =fs.read()) != -1) {
	     os.write(b);
	    }
        os.close();
        fs.close();

		book.seteBookLink(ebookLinkTarget);
		
		return libManageInerface.insertEbookDetails(book);
		
		}
	
	
	@GET
	@Path("/listOfEBooks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ebook> displayEBooks() throws Exception {
		// TODO Auto-generated method stub
		return libManageInerface.displayEBooks();
	}
	
	//*************************USER DETAILS**************************//
	@GET
	 @Path("/userDetails")
	 @Produces(MediaType.APPLICATION_JSON)
	public List<User> retriveUserDetails()
	  throws LibraryManagementCustomException 
	  {
	 // TODO Auto-generated method stub
	 return libManageInerface.retriveUserDetails();
	  }
	  
	 @POST
	 @Path("/deleteDetails")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String deleteUser(String userName) throws LibraryManagementCustomException
	 {
	  // TODO Auto-generated method stub
	  return libManageInerface.deleteUser(userName);
	 }
	 
	 
	 //********************************INSERT BOOK DETAILS***********************//
	 
	 @POST
	 @Path("/insertBook")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	  
	 public String insertBookDetails(JSONObject insertdataObj) throws JSONException, LibraryManagementCustomException
	 {
	 String bId=insertdataObj.getString("bookId");
	 String bName=insertdataObj.getString("bookName");
	 String bAuthor=insertdataObj.getString("bookAuthor");
	 String bCategory=insertdataObj.getString("bookCategory");
	 String bPublisher=insertdataObj.getString("bookPublisher");
	 System.out.println("json passed"+bName);
	 Book b=new Book();
	 b.setBookId(bId);
	 b.setBookName(bName);
	 b.setBookAuthor(bAuthor);
	 b.setBookCategory(bCategory);
	 b.setBookPublisher(bPublisher);
	 return libManageInerface.insertBookDetails(b);
	 } 
	 
	 
	 //************************************DISPLAY BOOK DETAILS******************************//
	 @GET
	 @Path("/displayBook")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Book> retriveBookDetails() throws LibraryManagementCustomException {
		// TODO Auto-generated method stub
		 return libManageInerface.retriveBookDetails();
		 }

	 
	 //******************issue books*******************//
	 
	 
	 @POST
	 @Path("/issuebook")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.APPLICATION_JSON)
	 public String issueBook(@FormParam("bookId") String bookId1,
	 @FormParam("username") String userName,
	 @FormParam("issuedate") String issueDate,
	 @FormParam("returndate") String returnDate) throws LibraryManagementCustomException {
	 // TODO Auto-generated method stub
	 System.out.println(bookId1);
	 System.out.println(userName);
	 System.out.println(issueDate);
	 System.out.println(returnDate);
	 Book bk=new Book();
	 bk.setBookId(bookId1);
	 bk.setIssueDate(issueDate);
	 bk.setReturnDate(returnDate);
	 User us=new User();
	 us.setUserName(userName);
	  
	 return libManageInerface.issueBook(bookId1,userName,issueDate,returnDate);
	 }
	 @POST
	 @Path("/returnbook")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.APPLICATION_JSON)
	 public String returnBook(@FormParam("bookId") String bookId) throws LibraryManagementCustomException {
	 // TODO Auto-generated method stub
	 System.out.println(bookId);
	 Book bk=new Book();
	 bk.setBookId(bookId);
	  
	 return libManageInerface.returnBook(bookId);
	 }


}
