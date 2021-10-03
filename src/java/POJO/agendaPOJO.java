package POJO;

import java.util.Date;

public class agendaPOJO {
    private Integer ID;
    private String Titulo;
    private String Evento;
    private String descricao;
    private String Src;
    private Date DataEvento;
    
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getEvento() {
        return Evento;
    }

    public void setEvento(String Evento) {
        this.Evento = Evento;
    }

    public Date getDataEvento() {
        return DataEvento;
    }


    public void setDataEvento(Date DataEvento) {
        this.DataEvento = DataEvento;
    }


    public String getSrc() {
        return Src;
    }


    public void setSrc(String Src) {
        this.Src = Src;
    }
    
    public String getdescricao() {
        return descricao;
    }

    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }
}
