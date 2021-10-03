/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.Date;

/**
 *
 * @author usuario
 */
public class conteudoPOJO {
    private Integer ID;  
    private Integer Id_Paginas;
    private String Titulo_Paginas;
    private String Titulo;
    private String Conteudo;
    private Date Inicio;
    private Date Fim;

    /**
     * @return the ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * @return the Titulo
     */
    public String getTitulo() {
        return Titulo;
    }

    /**
     * @param Titulo the Titulo to set
     */
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    /**
     * @return the Conteudo
     */
    public String getConteudo() {
        return Conteudo;
    }

    /**
     * @param Conteudo the Conteudo to set
     */
    public void setConteudo(String Conteudo) {
        this.Conteudo = Conteudo;
    }

    /**
     * @return the Inicio
     */
    public Date getInicio() {
        return Inicio;
    }

    /**
     * @param Inicio the Inicio to set
     */
    public void setInicio(Date Inicio) {
        this.Inicio = Inicio;
    }

    /**
     * @return the Fim
     */
    public Date getFim() {
        return Fim;
    }

    /**
     * @param Fim the Fim to set
     */
    public void setFim(Date Fim) {
        this.Fim = Fim;
    }

    /**
     * @return the Id_Paginas
     */
    public Integer getId_Paginas() {
        return Id_Paginas;
    }

    /**
     * @param Id_Paginas the Id_Paginas to set
     */
    public void setId_Paginas(Integer Id_Paginas) {
        this.Id_Paginas = Id_Paginas;
    }

    /**
     * @return the Titulo_Paginas
     */
    public String getTitulo_Paginas() {
        return Titulo_Paginas;
    }

    /**
     * @param Titulo_Paginas the Titulo_Paginas to set
     */
    public void setTitulo_Paginas(String Titulo_Paginas) {
        this.Titulo_Paginas = Titulo_Paginas;
    }
}
