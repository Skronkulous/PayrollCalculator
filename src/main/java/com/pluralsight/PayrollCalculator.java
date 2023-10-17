package com.pluralsight;
import java.io.*;
import java.util.Scanner;

public class PayrollCalculator {
    public static Employees[] employeeArray;
    public static void main(String[] args) {
        csvReader();
    }
    public static void csvReader(){
        try {
            FileReader fr = new FileReader("src/main/resources/employees.csv");
            String tempInfo = "";
            int temp = 0;
            Scanner lengthReader = new Scanner(fr);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            employeeArray = new Employees[9];
            while((tempInfo = br.readLine()) != null){
                String[] splitLine = tempInfo.split("\\|");
                   int id = (Integer.parseInt((splitLine[0]).trim()));
                   String name = (splitLine[1]);
                   double hoursWorked = (Double.parseDouble((splitLine[2]).trim()));
                   double payRate = (Double.parseDouble((splitLine[3]).trim()));
                   Employees newEmployee = new Employees(id, name, hoursWorked, payRate);
                   employeeArray[temp] = newEmployee;
                   temp++;
            }
        }
        catch(Exception e){
            System.out.println("Please fix file path/name. ");
            csvReader();
        }
        displayEmployees();
    }
    public static void displayEmployees(){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter the ID of the employee you would like to see: ");
            String choice = scan.nextLine();
            for (Employees employees : employeeArray) {
                if (Integer.parseInt(choice) == (employees.getId())) {
                    System.out.printf("Employee Id: %d\nEmployee Name: %s\nEmployee Gross Pay: $%.2f\n\n",employees.getId(),employees.getName(),employees.getGrossPay());
                    break;
                }
            }
            displayAnother();
        }
        catch(Exception f){
            System.out.println("Please enter a valid ID. ");
            displayEmployees();
        }
    }
    public static void displayAnother(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Would you like to display another employee? (1 or 2)\n\t1)Yes\n\t2)No\nUser Input: ");
        String choice = scan.nextLine();
        switch(Integer.parseInt(choice)){
            case 1:
                displayEmployees();
                break;
            case 2:
                System.out.println("See you again soon!");
                break;
            default:
                System.out.println("Please enter a valid input (1 or 2). \n");
                displayAnother();
                break;
        }
    }
}
