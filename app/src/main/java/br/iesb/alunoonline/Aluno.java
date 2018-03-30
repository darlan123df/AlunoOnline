package br.iesb.alunoonline;


import java.io.Serializable;
import java.util.ArrayList;

public class Aluno implements Serializable {
    public String email;
    public String nome;
    public int matricula;
    public String curso;
    public String campus;
    public Boolean ativo;

    public  ArrayList<Interesses> interesses = new ArrayList<>();
}