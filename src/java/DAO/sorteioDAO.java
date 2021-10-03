/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.contatoBEAN;
import BEAN.sorteioBEAN;
import POJO.sorteioPOJO;
import excecoes.Excecao;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class sorteioDAO {
    private Conexao MinhaConexao=null;
    public sorteioDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }    
    private sorteioBEAN inserir(sorteioBEAN sorteio)throws Exception{
        
        String SQL="insert into sorteio(nome,email,telefone) values("
                +Conexao.sqlProtection(sorteio.getNome())+","
                +Conexao.sqlProtection(sorteio.getEmail())+","
                +Conexao.sqlProtection(sorteio.getTelefone())+""
        + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            sorteio=buscarUltimo();
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            sorteio=new sorteioBEAN();
            throw new Exception("Erro ao cadastrar Sorteado.");
        }
        return sorteio;
    }
    private sorteioBEAN alterar(sorteioBEAN sorteio)throws Exception{
        String SQL="update sorteio set "
                + "nome="+Conexao.sqlProtection(sorteio.getNome()) +", "
                + "telefone="+Conexao.sqlProtection(sorteio.getTelefone()) +", "
                + "email="+Conexao.sqlProtection(sorteio.getEmail()) +" "
        + "where(id="+sorteio.getIDStr()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            sorteio=buscarID(sorteio);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            sorteio=new sorteioBEAN();
            throw new Exception("Erro ao alterar sorteado.");
        }
        return sorteio;
    }
    public sorteioBEAN salvar(sorteioBEAN sorteio) throws Exception{
        try{
            if(buscarID(sorteio).getIDStr()==null){
                if(buscarEmail(sorteio).size()>0)
                    throw new Exception("Email já cadastrado.");
                sorteio=inserir(sorteio);
            }
            else
                sorteio=alterar(sorteio);
        }
        catch(Exception erro){
            sorteio=new sorteioBEAN();
            throw new Exception(erro.getMessage());
        }
        return sorteio;
    }
    public void excluir(sorteioBEAN sorteio)throws Exception{
        String SQL="delete from sorteio where(id="+sorteio.getIDStr()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir usuário."+erro.getMessage());
        }
    }
    public Vector buscarCampos(String Nome,String Email,String Telefone)throws Excecao{
        try{ 
            int i=0;
            String condicoes="";
            String SQL="select id,nome,email,telefone from sorteio ";
            if ((Nome!=null)&&(Nome.length()>0))
                condicoes+= "(Nome like'%"+Nome.replaceAll("'","''") +"%')";
            if ((Email!=null)&&(Email.length()>0)){
                if (condicoes.length()>0)
                    condicoes+="and";
                condicoes+="(Email like'%"+Email.replaceAll("'","''") +"%')";
            }
            if ((Telefone!=null)&&(Telefone.length()>0)){
                if (condicoes.length()>0)
                    condicoes+="and";
                condicoes+="(Telefone like'%"+Telefone.replaceAll("'","''") +"%')";
            }
            if ((condicoes!=null)&&(condicoes.length()>0))
                SQL+=" where "+condicoes;
                
            return buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
        }
    } 
    
    public Vector buscarEmail(sorteioBEAN sorteio)throws Excecao{
        try{ 
            int i=0;
            String condicoes="";
            String SQL="select id,nome,email,telefone from sorteio ";
            SQL+=" where (email="+Conexao.sqlProtection(sorteio.getEmail())+")";
                
            return buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar nome! ERRO:"+erro.getMessage());
        }
    }
    public sorteioBEAN buscarUltimo()throws Excecao{
        String SQL="select id,nome,email,telefone from sorteio order by id desc";
        sorteioBEAN sorteio=new sorteioBEAN();
        Vector registros=new Vector();
        try{ 
            registros=buscar(SQL);
            if ((registros!=null)&&(registros.size()!=0))
                sorteio=(sorteioBEAN)registros.get(0);
       }
       catch(Exception erro){           
           throw new Excecao("Erro ao buscar Ultimo! ERRO:"+SQL);
       }
       return sorteio;
     }
      public sorteioBEAN buscarID(sorteioBEAN sorteio)throws Excecao{
        String SQL="select id,nome,email,telefone from sorteio where(ID="+sorteio.getIDStr()+");";
        sorteio=new sorteioBEAN();
        Vector registros=new Vector();
        try{ 
            registros=buscar(SQL);
            if ((registros!=null)&&(registros.size()!=0))
                sorteio=(sorteioBEAN)registros.get(0);
       }
       catch(Exception erro){           
           throw new Excecao("Erro ao buscar ID! ERRO:"+SQL);
       }
       return sorteio;
     }
     public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar("select id,nome,email,telefone from sorteio;");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar todos! ERRO:"+erro.getMessage());
       }
     }
     private Vector buscar(String SQL)throws Excecao{
        Vector sorteios=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,nome,email,telefone from sorteio;";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       sorteioPOJO sorteio = new  sorteioPOJO();
                       sorteio.setID(MinhaConexao.MostrarCampoInteger("ID"));
                       sorteio.setTelefone(MinhaConexao.MostrarCampoStr("Telefone"));
                       sorteio.setNome(MinhaConexao.MostrarCampoStr("Nome"));
                       sorteio.setEmail(MinhaConexao.MostrarCampoStr("Email"));
                       sorteioBEAN sorteio_BEAN= new sorteioBEAN(sorteio);
                       sorteios.add(sorteio_BEAN);
                  }      
            }
            return sorteios;
       }    
       catch (Exception erro)
       {
            throw new Excecao("Erro ao buscar ");
       }
    }  
    public void limparSorteio()throws Excecao{
        try{ 
            Vector acc= buscarTodos();
            for(int i=0;i<acc.size();i++){
                sorteioBEAN sorteio_BEAN=(sorteioBEAN)acc.get(i);
                Date Hoje = new Date();
                contatoBEAN contato_bean =new contatoBEAN();
                contatoDAO contato_dao = new contatoDAO(MinhaConexao);
                contato_bean.setAtivo(true);
                contato_bean.setData_Cadastro(Hoje);
                contato_bean.setEmail(sorteio_BEAN.getEmail());
                contato_bean.setNome(sorteio_BEAN.getNome());
                contato_dao.salvar(contato_bean);
                excluir(sorteio_BEAN);
            }
        }
        catch(Exception erro){
            throw new Excecao("Erro ao Limpar todos! ERRO:"+erro.getMessage());
        }
    }
}


