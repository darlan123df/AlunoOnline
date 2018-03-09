package br.iesb.alunoonline;


import java.util.ArrayList;

public class Aluno {
    public String nome;
    public int matricula;
    public String curso;
    public String campus;
    public Boolean ativo;

    public  ArrayList<Interesses> interesses = new ArrayList<>();
}