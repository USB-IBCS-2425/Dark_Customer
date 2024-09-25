public class Wallet {
    
    private double money;
    private int IDnumber;

   
    public Wallet(double money, int IDnumber) {
        this.money = money;
        this.IDnumber = IDnumber;
    }

    
    public double getMoney() {
        return money;
    }

    
    public void setMoney(double money) {
        this.money = money;
    }

    
    public int getIDnumber() {
        return IDnumber;
    }

    
    public void setIDnumber(int IDnumber) {
        this.IDnumber = IDnumber;
    }

    
    public void payday(double amount) {
        money += amount;
    }

    
    public void pay(double amount) {
        if (money >= amount) {
            money -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    
    public static void main(String[] args) {
        Wallet myWallet = new Wallet(100, 123456);
        System.out.println("Initial money: " + myWallet.getMoney());
        myWallet.payday(50);
        System.out.println("After payday: " + myWallet.getMoney());
        myWallet.pay(30);
        System.out.println("After paying 30: " + myWallet.getMoney());
        myWallet.pay(200); 
    }
}
