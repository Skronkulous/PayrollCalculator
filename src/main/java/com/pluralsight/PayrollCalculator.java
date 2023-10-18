package com.pluralsight;
import java.io.*;
import java.util.Scanner;

public class PayrollCalculator {
    public static Employees[] employeeArray;
    public static void main(String[] args){
        csvReader();
    }
    public static void csvReader(){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter the file you would like to edit/read (ending with . extention): ");
            String fileChoice = (scan.nextLine()).trim();
            FileReader fr = new FileReader("src/main/resources/" + fileChoice);
            String tempInfo = "";
            int temp = 0;
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
        displayChoices();
    }
    public static void csvWriter(){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter the name of the payroll file you would like to create (ending with . extention): ");
            String fileChoice = (scan.nextLine()).trim();
            File file = new File("src/main/resources/" + fileChoice);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("id|name|gross-pay");
            bw.newLine();
            String[] extensionChecker = fileChoice.split("\\.");
            if(extensionChecker[1].equalsIgnoreCase("json")){
                for(int j = 0; j < 8; j++){
                    if(j == 0){
                        bw.write("[");
                        bw.newLine();
                    }
                    bw.write("  { \"id\": ");
                    bw.write(Integer.toString(employeeArray[j].getId()));
                    bw.write(", \"name\": \"");
                    bw.write(employeeArray[j].getName());
                    bw.write("\", \"grossPay\" : ");
                    bw.write(Double.toString(employeeArray[j].getGrossPay()));
                    bw.write("}");
                    if(j != 7){
                        bw.write(",");
                        bw.newLine();
                    }
                    else{
                        bw.newLine();
                        bw.write("]");
                    }
                }
            }
            else{
                for(int j = 0; j < 8; j++){
                    bw.write(Integer.toString(employeeArray[j].getId()));
                    bw.write("|");
                    bw.write(employeeArray[j].getName());
                    bw.write("|");
                    bw.write(Double.toString(employeeArray[j].getGrossPay()));
                    bw.newLine();
                }
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println("An unknown error occurred, please try again.");
            csvWriter();
        }
        System.out.println("Your new file was made successfully! \n");
        displayChoices();
    }
    public static void displayChoices(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please choose what you would like to do (1, 2, or 3).\n\t1)Create Payroll File\n\t2)Output Employee Info\n\t3)Exit Program\nUser Input: ");
        String choice = scan.nextLine();
        switch(choice){
            case("1"):
                csvWriter();
                break;
            case("2"):
                displayEmployees();
                break;
            case("3"):
                System.out.println("See you again soon, thank you!");
                break;
            default:
                System.out.println("Please choose a valid option (1, 2, or 3).");
                displayChoices();
        }
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
