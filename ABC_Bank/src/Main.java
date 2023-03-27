import java.util.*;

class BankAccount {
    protected static String bankName = "ABC BANK";
    protected String fullName;
    protected int accNo;
    protected String accType;
    protected double interestRate;
    protected double balance;
    protected String status;

    public BankAccount () {
        this.fullName = "N/A";
        Random random = new Random();
        this.accNo = random.nextInt(1001,10000);
        this.accType = "N/A";
        this.interestRate = 0.0;
        this.balance = 0.0;
    }

    public void setFullName() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your full name: ");
        this.fullName = scan.nextLine();
    }

    //public void setStatus(String status) {
    //    this.status = "[Successful|Unsuccessful]";
    //}

    public String getStatus() {
        return status;
    }

    public static String getBankName() {
        return bankName;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAccNo() {
        return accNo;
    }

    public String getAccType() {
        return accType;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit (double amt) {
        this.status = "Successful";
        balance += amt;
    }

    public void withdrawal (double amt) {
        try {
            if (amt > balance) {
                throw new ArithmeticException("Error: the amount is bigger than the balance.");
            }
            this.status = "Successful";
            balance -= amt;
        }catch (ArithmeticException e) {
            this.status = "Unsuccessful";
            System.out.println(e.getMessage());
        }
    }

    public void transfer (int p, double amt) {
        if (p == 0)
            withdrawal(amt);
        else
            deposit(amt);
    }

    public String display () {
        return "\n::" + this.getBankName() + "::" +
                "\nName: \t" + this.getFullName() +
                "\nAccount No.: \t" + this.getAccNo() +
                "\nAccount Type: \t" + this.getAccType() +
                "\nInterest Rate: \t" + this.getInterestRate() * 100 + "%" +
                "\nBalance: \tRM" + this.getBalance();
    }


    public String toString() {
        return display() + "\nTransaction Status: \t" + status;
    }
    static {
        System.out.println("==WELCOME TO BANK ABC==");
    }
}
class SavingsAccount extends BankAccount {
    public SavingsAccount(String n){
        super();
        System.out.println("Generating account...");
        this.fullName = n;
        this.accType = "Savings Account";
        this.interestRate = 0.0005;
        this.balance = 50.00;
    }
}
class CurrentAccount extends BankAccount {
    public CurrentAccount(String n) {
        super();
        System.out.println("Generating account...");
        this.fullName = n;
        this.accType = "Current Account";
        this.interestRate = 0.0001;
        this.balance = 200.00;
    }
}
public class Main {
    public static int findAccNo (BankAccount[]p, Scanner s, int no) {
        no = 0;
        int id;
        while (true) {
            try {
                System.out.print("Enter account no.: ");
                id = s.nextInt();
                while (true) {
                    if (p[no].getAccNo() == id)
                        break;
                    no++;
                }
                break;
            }catch (InputMismatchException e) {
                System.out.println("ONLY numbers!!");
                s.next();
            }catch (NullPointerException e) {
                System.out.println("Account does not exist!");
                no = 0;
            }
        }
        return no;
    }
    public static double getAmt(Scanner s) {
        double amt;
        while (true) {
            try {
                System.out.print("Amount: ");
                amt = s.nextDouble();
                break;
            }catch (InputMismatchException e) {
                System.out.println("ONLY enter integers");
                s.next();
            }
        }
        return amt;
    }

    public static void main(String[] args) {
        BankAccount[] p = new BankAccount[100];
        int choice, trans, i = 0;
        Scanner scan = new Scanner(System.in);
        do {
            p[i] = new BankAccount();
            while(true){
                try {
                    System.out.println("\n::" + p[0].getBankName() + "::");
                    System.out.println("1. Create new account");
                    System.out.println("2. Make a transaction");
                    System.out.println("0. End the program");
                    System.out.print("Enter action: ");
                    choice = scan.nextInt();
                    if (choice != 0 && choice != 1 && choice != 2)
                        throw new ArithmeticException("Invalid input. Please enter 1-2. Please enter 0 to exit");
                    break;
                }catch (ArithmeticException e) {
                    System.out.println(e.getMessage());
                }catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter 1-2.");
                    scan.next();
                }
            }

            switch (choice) {
                case 1:
                    while (true) {
                        try {
                            String n;
                            p[i].setFullName();
                            n = p[i].getFullName();
                            System.out.println("\n::Types of Account::");
                            System.out.println("1. Savings Account [initial: RM50]");
                            System.out.println("2. Current Account [initial: RM200]");
                            System.out.print("Enter type: ");
                            trans = scan.nextInt();
                            if (trans != 1 && trans != 2)
                                throw new ArithmeticException("Invalid input. Please enter 1-2.");
                            if (trans == 1) {
                                p[i] = new SavingsAccount(n);
                            } else {
                                p[i] = new CurrentAccount(n);
                            }
                            System.out.println(p[i].display());
                            break;
                        }catch (ArithmeticException e) {
                            System.out.println(e.getMessage());
                        }catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter 1-2.");
                            scan.next();
                        }
                    }
                    i++;
                    break;
                case 2:
                    int acc = 0;
                    double amt;
                    acc = findAccNo(p, scan, acc);
                    try {
                        System.out.println("\n::Transactions Available::");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Transfer");
                        System.out.println("4. Display");
                        System.out.print("Enter action: ");
                        trans = scan.nextInt();
                        if (trans < 1 && trans > 4)
                            throw new ArithmeticException("Invalid input. Please enter 1-4.");
                        switch (trans) {
                            case 1:
                                amt = getAmt(scan);
                                p[acc].deposit(amt);
                                System.out.println(p[acc]);
                                break;
                            case 2:
                                amt = getAmt(scan);
                                p[acc].withdrawal(amt);
                                System.out.println(p[acc]);
                                break;
                            case 3:
                                amt = getAmt(scan);
                                int acc2 = 0;
                                System.out.println("==Account to transfer==");
                                acc2 = findAccNo(p, scan, acc);
                                for (int ppl = 0; ppl < 2; ppl++) {
                                    if(ppl == 0)
                                        p[acc].transfer(ppl, amt);
                                    else
                                        p[acc2].transfer(ppl, amt);
                                }
                                System.out.println(p[acc]);
                                break;
                            case 4:
                                System.out.println(p[acc].display());
                        }
                    }catch(ArithmeticException e){
                        System.out.println(e.getMessage());
                        scan.nextInt();
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter 1-.");
                        scan.next();
                    }
                    break;
                case 0:
                    System.out.println("::End of Program::");
                    break;
            }
        }while(choice != 0);
    }
}