package modelo;

public class Cuenta {

    private int numeroCuenta;
    private String titular;
    private String fechApertura;
    private double saldo;
    private String nacionalidad;

    public Cuenta(int numeroCuenta, String titular, String fechApertura, double saldo, String nacionalidad) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.fechApertura = fechApertura;
        this.saldo = saldo;
        this.nacionalidad=nacionalidad;
    }



    public Cuenta (){

    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getFechApertura() {
        return fechApertura;
    }

    public void setFechApertura(String fechApertura) {
        this.fechApertura = fechApertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNacionalidad(){
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad){
        this.nacionalidad=nacionalidad;
    }

}
