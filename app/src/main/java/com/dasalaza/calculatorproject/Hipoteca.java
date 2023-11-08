package com.dasalaza.calculatorproject;

public class Hipoteca {
    private int precioImmoble;
    private int estalvis;
    private int plazo;
    private double euribor;
    private double diferencial;

    public Hipoteca() {
    }

    public Hipoteca(int precioImmoble, int estalvis, int plazo, double euribor, double diferencial) {
        this.precioImmoble = precioImmoble;
        this.estalvis = estalvis;
        this.plazo = plazo;
        this.euribor = euribor;
        this.diferencial = diferencial;
    }

    public int getPrecioImmoble() {
        return precioImmoble;
    }

    public void setPrecioImmoble(int precioImmoble) {
        this.precioImmoble = precioImmoble;
    }

    public int getEstalvis() {
        return estalvis;
    }

    public void setEstalvis(int estalvis) {
        this.estalvis = estalvis;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public double getEuribor() {
        return euribor;
    }

    public void setEuribor(double euribor) {
        this.euribor = euribor;
    }

    public double getDiferencial() {
        return diferencial;
    }

    public void setDiferencial(double diferencial) {
        this.diferencial = diferencial;
    }

    public double calcularImporteHipoteca()
    {

        return 0;
    }

}
