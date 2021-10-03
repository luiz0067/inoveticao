/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.estadoBEAN;
import POJO.estadoPOJO;
import excecoes.Excecao;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class estadoDAO {
    private Conexao MinhaConexao=null;
    public estadoDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public void inserir(estadoBEAN estado)throws Exception{
        String SQL="insert into estado(sigla,nome)values("
                +Conexao.sqlProtection(estado.getSigla())+","
                +Conexao.sqlProtection(estado.getNome())+""
                +")";
                try{
                    this.MinhaConexao.Executa(SQL);
                    this.MinhaConexao.Executa("commit");
                }
                catch (Exception erro){
                    throw new Exception("Erro ao cadastrar estado."+erro.getMessage());
                }   
    }
    public void alterar(estadoBEAN estado)throws Exception{
        String SQL="update estado set nome="+Conexao.sqlProtection(estado.getNome())+", sigla="+Conexao.sqlProtection(estado.getSigla())+" where(id="+estado.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao alterar estado."+erro.getMessage());
        }
    }
    public void excluir(estadoBEAN estado)throws Exception{
        String SQL="delete from estado where(id="+estado.getIDStr()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
    
        catch(Exception erro){
            throw new Exception("Erro ao excluir estado."+erro.getMessage());
        }
    }
    private Vector buscar(String SQL)throws Excecao{
        Vector estados=new Vector();
            try{
                if(SQL==null)
                    SQL = "SELECT id,sigla,nome from estado";                   
                    if(MinhaConexao.Busca(SQL)){
                          while(MinhaConexao.MoverProximo())
                          {
                               estadoPOJO estado = new  estadoPOJO();
                               estado.setID(MinhaConexao.MostrarCampoInteger("ID"));
                               estado.setNome(MinhaConexao.MostrarCampoStr("Nome"));
                               estado.setSigla(MinhaConexao.MostrarCampoStr("Sigla"));
                               estadoBEAN estado_BEAN = new estadoBEAN(estado);
                               estados.add(estado_BEAN);
                          }                                                  
            }
            return estados;
            }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar estado! ERRO: "+ erro.getMessage());
        }
    }
     public Vector buscarNome(String Nome)throws Excecao{
       try{ 
         String SQL="select id,nome,sigla from estado where(nome like'%"+Nome.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     }  
     public estadoBEAN buscarUltimo()throws Excecao{
       try{ 
         String SQL="select id,nome,sigla from estado order by id desc";
         
         return ((estadoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     }   
     public estadoBEAN buscarID(int ID)throws Excecao{
       try{ 
         String SQL="select id,nome,sigla from estado where(ID="+ID+");";
         return ((estadoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     }     
      public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(null);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     }            
}
