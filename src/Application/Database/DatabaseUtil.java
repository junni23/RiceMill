package Application.Database;

import Application.Model.Account;
import Application.Model.Employee;
import Application.Model.Salary;
import Application.Model.Transaction;
import Application.TableModel.SalaryData;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sohel on 5/28/2017.
 */
public class DatabaseUtil {

    private static final String EMPLOYEE_TABLE = "tbl_employee";
    private static final String ACCOUNT_TABLE = "tbl_accounts";
    private static final String TRANSACTION_TABLE = "tbl_transaction";
    private static final String SALARY_TABLE = "tbl_salary";

    private DatabaseHandler handler;
    private static DatabaseUtil databaseUtil=null;

    private DatabaseUtil() {
        handler = DatabaseHandler.getInstance();
    }

    public static DatabaseUtil getInstance(){
        if(databaseUtil==null){
            databaseUtil= new DatabaseUtil();
        }
        return databaseUtil;
    }

    public boolean insertEmployee(Employee employee){

        String query = "INSERT INTO " +EMPLOYEE_TABLE+ " (employee_name,employee_fathers_name,employee_address,employee_contact,employee_email,employee_designation,employee_photo,employee_salary) VALUES ("
                +"'" +employee.getName()+ "', "
                +"'" +employee.getFathersName()+ "', "
                +"'" +employee.getAddress()+ "', "
                +"'" +employee.getContact()+ "', "
                +"'" +employee.getEmail()+ "', "
                +"'" +employee.getDesignation()+ "', "
                +"'" +employee.getPicture()+ "', "
                + employee.getSalary()
                +")";

        return handler.execAction(query);

    }

    public boolean insertAccount(Account account){
        String query =  "INSERT INTO " +ACCOUNT_TABLE+" (account_name,address,contact,email,account_type,is_loan_account) VALUES ("
                +"'"+account.getAccountName()+"', "
                +"'"+account.getAddress()+"', "
                +"'"+account.getContact()+"', "
                +"'"+account.getEmail()+"', "
                +account.getAccount_type()+", "
                +account.getIs_load_account()
                +")";

        return handler.execAction(query);

    }

    public List<Account> getAllAccounts(){
        List<Account> accountList = new ArrayList<>();
        try{
            String query = "SELECT * FROM "+ ACCOUNT_TABLE+" ORDER BY account_name";
            ResultSet rs = handler.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("id") ;
                String name = rs.getString("account_name");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String email = rs.getString("email");
                int acctype = rs.getInt("account_type");
                int isLoan_acc = rs.getInt("is_loan_account");

                //System.out.println(name);

                Account account = new Account(id,name,address,contact,email,acctype,isLoan_acc);
                accountList.add(account);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return accountList;

    }

    public List<Account> getAllBankAccounts(){
        List<Account> accountList = new ArrayList<>();
        try{
            String query = "SELECT * FROM "+ ACCOUNT_TABLE+" WHERE account_type=3 ORDER BY account_name";
            ResultSet rs = handler.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("id") ;
                String name = rs.getString("account_name");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String email = rs.getString("email");
                int acctype = rs.getInt("account_type");
                int isLoan_acc = rs.getInt("is_loan_account");

                //System.out.println(name);

                Account account = new Account(id,name,address,contact,email,acctype,isLoan_acc);
                accountList.add(account);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return accountList;

    }

    public String getLastTransactionInvoice(){
        String retStr ="";

        String query = "SELECT invoice_number FROM "+TRANSACTION_TABLE+" WHERE transaction_id= (SELECT MAX(transaction_id) FROM "+TRANSACTION_TABLE+")";
        //System.out.println(query);

        ResultSet resultSet = handler.executeQuery(query);

        try{
            while (resultSet.next()){
                retStr = resultSet.getString("invoice_number");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return retStr;
    }

    public boolean transactEmployeeSalary(Salary salary){
        boolean retbool = false;
        String query = "INSERT INTO "+SALARY_TABLE+" (employee_id,paid_salary_month,salary_amount) VALUES("
                +salary.getEmployee().getId()+","
                +"'"+salary.getMonth_year()+"',"
                +salary.getEmployee().getSalary()
                +")";

        if(handler.execAction(query)){
            // Create and Add Transaction
            Transaction transaction = new Transaction(salary.getInvoice_number(),"Paid Salary to "+salary.getEmployee().getName(),salary.getAccount(),getAccountByName("Salary"),salary.getEmployee().getSalary());
            retbool =insertTransaction(transaction);
        }

        return retbool;

    }

    public List<SalaryData> getSalaryData(int employeeId){
        List<SalaryData> retList = new ArrayList<>();
        String query = "SELECT * FROM "+SALARY_TABLE+" WHERE employee_id='"+employeeId+"'";

        ResultSet resultSet = handler.executeQuery(query);

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        try{
            while (resultSet.next()){
                int id = resultSet.getInt("salary_id");
                Timestamp date = resultSet.getTimestamp("salary_date");
                int employee_Id = resultSet.getInt("employee_id");
                String salary_month = resultSet.getString("paid_salary_month");
                double amount = resultSet.getDouble("salary_amount");

                SalaryData salaryData = new SalaryData(id,dateFormat.format(date),employee_Id,salary_month,amount);
                retList.add(salaryData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return retList;
    }

    public Account getAccountByName(String accName){
        Account account = null;


        try{
            String query = "SELECT * FROM "+ACCOUNT_TABLE+" WHERE account_name='"+accName+"'";
            ResultSet rs = handler.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("id") ;
                String name = rs.getString("account_name");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String email = rs.getString("email");
                int acctype = rs.getInt("account_type");
                int isLoan_acc = rs.getInt("is_loan_account");

                System.out.println(name);

                account = new Account(id,name,address,contact,email,acctype,isLoan_acc);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return account;

    }

    public boolean insertTransaction(Transaction transaction){
        boolean retBool = false;
        String query1 = "INSERT INTO "+TRANSACTION_TABLE+" (invoice_number,description,account_id,transaction_type,amount) VALUES("
                +"'" +transaction.getInvoiceNumber()+ "', "
                +"'" +transaction.getDescription()+ "', "
                +transaction.getFrom().getId()+ ", "
                +1+ ", "// Credit
                +transaction.getAmount()
                +")";

        String query2 = "INSERT INTO "+TRANSACTION_TABLE+" (invoice_number,description,account_id,transaction_type,amount) VALUES("
                +"'" +transaction.getInvoiceNumber()+ "', "
                +"'" +transaction.getDescription()+ "', "
                +transaction.getTo().getId()+ ", "
                +0+ ", "//Debit
                +transaction.getAmount()
                +")";

        /*System.out.println(query1);
        System.out.println(query2);*/

        if(handler.execAction(query1) && handler.execAction(query2) ){
            retBool = true;
        }

        return retBool;
    }

    public boolean updateEmployee(Employee employee){
        String query = "UPDATE "+EMPLOYEE_TABLE+" SET"
                +" employee_name ='"+employee.getName()+"',"
                +" employee_fathers_name ='"+employee.getFathersName()+"',"
                +" employee_address ='"+employee.getAddress()+"',"
                +" employee_contact ='"+employee.getContact()+"',"
                +" employee_email ='"+employee.getEmail()+"',"
                +" employee_designation ='"+employee.getDesignation()+"',"
                +" employee_photo ='"+employee.getPicture()+"',"
                +" employee_salary ="+employee.getSalary()
                +" WHERE employee_id ="+employee.getId();


        return handler.execAction(query);
    }

    public List<Employee> getAllEmployee(){
        List<Employee> retList = new ArrayList<>();
        String query = "SELECT * FROM "+ EMPLOYEE_TABLE +" ORDER BY employee_name";

        ResultSet resultSet = handler.executeQuery(query);

        try{
            while (resultSet.next()){
                int id = resultSet.getInt("employee_id");
                String name = resultSet.getString("employee_name");
                String fathersName = resultSet.getString("employee_fathers_name");
                String employeeAddress = resultSet.getString("employee_address");
                String employeeContact = resultSet.getString("employee_contact");
                String employeeEmail = resultSet.getString("employee_email");
                String employeeDesignation = resultSet.getString("employee_designation");
                String employeePhoto = resultSet.getString("employee_photo");
                double employeeSalary = resultSet.getDouble("employee_salary");

                Employee employee =
                        new Employee(id,name,fathersName,employeeAddress,employeeContact,employeeEmail,employeeDesignation,employeePhoto,employeeSalary);

                retList.add(employee);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return retList;
    }


}
