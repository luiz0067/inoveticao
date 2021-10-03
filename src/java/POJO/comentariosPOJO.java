/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class comentariosPOJO {
    private Integer ID;
    private String Titulo;
    private String Descricao;
    private boolean Exibir;
    private Date Data_Cadastro;
    private Integer id_menu;

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
     * @return the Descricao
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * @param Descricao the Descricao to set
     */
    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    /**
     * @return the Exibir
     */
    public boolean isExibir() {
        return Exibir;
    }
    public boolean getExibir() {
        return Exibir;
    }    

    /**
     * @param Exibir the Exibir to set
     */
    public void setExibir(boolean Exibir) {
        this.Exibir = Exibir;
    }

    /**
     * @return the Data_Cadastro
     */
    public Date getData_Cadastro() {
        return Data_Cadastro;
    }

    /**
     * @param Data_Cadastro the Data_Cadastro to set
     */
    public void setData_Cadastro(Date Data_Cadastro) {
        this.Data_Cadastro = Data_Cadastro;
    }
    
    public Integer getid_menu() {
        return id_menu;
    }

    
    public void setid_menu(Integer id_menu) {
        this.id_menu = id_menu;
    }
    
}
