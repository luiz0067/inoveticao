/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.menuBEAN;
import POJO.menuPOJO;
import excecoes.Excecao;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class menuDAO {
    private Conexao MinhaConexao=null;
    public menuDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    } 
    
    private void inserir(menuBEAN menu)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into menu(titulo) values("+Conexao.sqlProtection(menu.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar menu."+erro.getMessage());}    
    }
    
    private void alterar(menuBEAN menu)throws Exception{
        try{
            this.MinhaConexao.Executa("update menu set titulo=" +Conexao.sqlProtection(menu.getTitulo()) +" where(id="+menu.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar Titulo da menu."+erro.getMessage());}          
    }
    public void excluir(menuBEAN menu)throws Exception{
        try{
            this.MinhaConexao.Executa("delete from menu where(id="+menu.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao excluir menu."+erro.getMessage());}
    }
    public menuBEAN salvar(menuBEAN menu)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((menu.getIDStr()!=null)&&(buscarID(menu).getIDStr()!=null)){
            alterar(menu);
            return buscarID(menu);
        }
        else{
           inserir(menu);
           return buscarUltimo();
        }
    }    
    
    public menuBEAN buscarUltimo()throws Excecao{
       menuBEAN ultimo = new menuBEAN();
       try{ultimo= ((menuBEAN)buscar("select id,titulo from menu order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public menuBEAN buscarPrimeiro()throws Excecao{
       menuBEAN ultimo = new menuBEAN();
       try{ultimo= ((menuBEAN)buscar("select id,titulo from menu order by titulo asc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public menuBEAN buscarID(menuBEAN menu)throws Excecao{
       menuBEAN registro = new menuBEAN();
       try{registro= ((menuBEAN)buscar("select id,titulo from menu where(id="+menu.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from menu ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:"+erro.getMessage());}
       return registros;

     }
    
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from menu where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:"+erro.getMessage());}
       return registros;
    }
     
   public Vector buscar(String SQL)throws Excecao{
        Vector menus=new Vector();
        try{
            if(SQL==null)
                SQL = "SELECT id,titulo from menu";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       menuPOJO POJO = new  menuPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("ID"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("Titulo"));                              
                       menuBEAN menu_BEAN= new menuBEAN(POJO);
                       menus.add(menu_BEAN);
                  }                                                  
            }
            return menus;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar menu! ERRO: "+ erro.getMessage());
        }
    }
    
}
