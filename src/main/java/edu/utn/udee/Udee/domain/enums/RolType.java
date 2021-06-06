package edu.utn.udee.Udee.domain.enums;

public enum RolType {

    CLIENT("CLIENT"),
    EMPLOYEE("EMPLOYEE");

    String description;

    RolType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /*@Override
    public String toString() {
        return "RolType{" +
                "description='" + description + '\'' +
                '}';
    }*/
}
