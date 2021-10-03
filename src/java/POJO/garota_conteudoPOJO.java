
package POJO;

import java.util.Date;


public class garota_conteudoPOJO {
    private Integer id;  
    private Integer id_garotapg;
    private String titulo_garotapg;
    private String titulo;
    private String garota_conteudo;
    private Date inicio;
    private Date fim;

    
    public Integer getid() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setid(Integer id) {
        this.id = id;
    }

    /**
     * @return the titulo
     */
    public String gettitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void settitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the garota_conteudo
     */
    public String getgarota_conteudo() {
        return garota_conteudo;
    }

    /**
     * @param garota_conteudo the garota_conteudo to set
     */
    public void setgarota_conteudo(String garota_conteudo) {
        this.garota_conteudo = garota_conteudo;
    }

    /**
     * @return the inicio
     */
    public Date getinicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setinicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fim
     */
    public Date getfim() {
        return fim;
    }

    /**
     * @param fim the fim to set
     */
    public void setfim(Date fim) {
        this.fim = fim;
    }

    /**
     * @return the id_garotapg
     */
    public Integer getid_garotapg() {
        return id_garotapg;
    }

    /**
     * @param id_garotapg the id_garotapg to set
     */
    public void setid_garotapg(Integer id_garotapg) {
        this.id_garotapg = id_garotapg;
    }

    /**
     * @return the titulo_garotapg
     */
    public String gettitulo_garotapg() {
        return titulo_garotapg;
    }

    /**
     * @param titulo_garotapg the titulo_garotapg to set
     */
    public void settitulo_garotapg(String titulo_garotapg) {
        this.titulo_garotapg = titulo_garotapg;
    }
}
