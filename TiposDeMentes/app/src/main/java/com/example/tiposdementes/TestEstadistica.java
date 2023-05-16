package com.example.tiposdementes;

public class TestEstadistica {

    private String nombre;
    private String carrera;
    private String mente;

    public TestEstadistica(String nombre, String carrera, String mente) {
        this.nombre = nombre;
        this.carrera = carrera;
        this.mente = mente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getMente() {
        return mente;
    }

    public void setMente(String mente) {
        this.mente = mente;
    }
}
