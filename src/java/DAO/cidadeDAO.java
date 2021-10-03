/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.cidadeBEAN;
import POJO.cidadePOJO;
import excecoes.Excecao;
import java.util.Vector;
/**
 *
 * @author PC
 */
public class cidadeDAO {
    private Conexao MinhaConexao=null;
    public cidadeDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public void salvar(cidadeBEAN cidade)throws Exception{
        String SQL="insert into cidade(nome,estado) values("
                +Conexao.sqlProtection(cidade.getNome())+","
                +Conexao.sqlProtection(cidade.getEstado())+","
                + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao cadastrar cidade."+erro.getMessage());
        }
    }
    public void inserir(cidadeBEAN cidade)throws Exception{
        String SQL="insert into cidade(estado,nome)values("
                +Conexao.sqlProtection(cidade.getEstado())+","
                +Conexao.sqlProtection(cidade.getNome())+""
                +")";
                try{
                    this.MinhaConexao.Executa(SQL);
                    this.MinhaConexao.Executa("commit");
                }
                catch (Exception erro){
                    throw new Exception("Erro ao cadastrar cidade."+erro.getMessage());
                }   
    }
    public void alterar(cidadeBEAN cidade)throws Exception{
        String SQL="update cidade set nome="+Conexao.sqlProtection(cidade.getNome())+", estado="+Conexao.sqlProtection(cidade.getEstado())+" where(id="+cidade.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao alterar cidade."+erro.getMessage());
        }
    }
    public Vector buscarNome(String Nome)throws Excecao{
       try{ 
         String SQL="select id,nome,estado from cidade where(nome like'%"+Nome.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     } 
    public cidadeBEAN buscarUltimo()throws Excecao{
       try{ 
         String SQL="select id,nome,estado from cidade order by id desc";
         
         return ((cidadeBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
       }
     }
    public cidadeBEAN buscarID(int ID)throws Excecao{
       try{ 
         String SQL="select id,nome,estado from cidade where(ID="+ID+");";
         return ((cidadeBEAN)buscar(SQL).get(0));
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
    public void excluir(cidadeBEAN cidade)throws Exception{
            String SQL="delete from cidade where(id="+cidade.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir cidade."+erro.getMessage());
        }
    }
    public Vector buscar(String SQL)throws Excecao{
       Vector cidades=new Vector();
            try{
                if(SQL==null)
                    SQL = "SELECT id,nome,estado from cidade";                   
                    if(MinhaConexao.Busca(SQL)){
                          while(MinhaConexao.MoverProximo())
                          {
                               cidadePOJO cidade = new  cidadePOJO();
                               cidade.setID(MinhaConexao.MostrarCampoInteger("ID"));
                               cidade.setNome(MinhaConexao.MostrarCampoStr("Nome"));
                               cidade.setEstado(MinhaConexao.MostrarCampoInteger("Estado")); 
                               cidadeBEAN cidade_BEAN = new cidadeBEAN(cidade);
                               cidades.add(cidade_BEAN);
                          }                     
                              
            }
            return cidades;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar cidade! ERRO: "+ erro.getMessage());
        }
    }

           
    
}
