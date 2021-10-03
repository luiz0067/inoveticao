/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.paginasBEAN;
import POJO.paginasPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class paginasDAO {
    private Conexao MinhaConexao=null;
    private String SELECT="select id,titulo,src from paginas ";
    public paginasDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }    
    private paginasBEAN inserir(paginasBEAN paginas)throws Exception{
        try{
            
            String SQL="insert into paginas(titulo,src) values("
                    + ""+Conexao.sqlProtection(paginas.getTitulo())+""
                    + ","
                    + ""+Conexao.sqlProtection(paginas.getSrc())+""
                    + ")";
            if (paginas.getSrc()==null){
                SQL="insert into paginas(titulo) values("
                    + ""+Conexao.sqlProtection(paginas.getTitulo())+""
                    
                    + ")";
            }
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return buscarUltimo();
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar paginas."+erro.getMessage());}    
    }    
    private paginasBEAN alterar(paginasBEAN paginas,String path_upload)throws Exception{
        try{
            String SQL="update paginas set ";
                    SQL+= "titulo=" +Conexao.sqlProtection(paginas.getTitulo()) +"";
                    if(paginas.getSrc()!=null){
                        SQL+= ",";
                        SQL+= "src="+Conexao.sqlProtection(paginas.getSrc())+ "";
                    }
                    SQL+= " where(id="+paginas.getID()+")";
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            paginas=buscarID(paginas);
            if(paginas.getIDStr()!=null){
                if (paginas.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,paginas.getSrc());
                }
            }               
            return buscarID(paginas);
        }
        catch(Exception erro){throw new Exception("Erro ao alterar Titulo da paginas."+erro.getMessage());}          
    }
    public paginasBEAN excluir(paginasBEAN paginas,String path_upload)throws Exception{
        try{
            paginas=buscarID(paginas);
            if(paginas.getIDStr()!=null){
                if (paginas.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,paginas.getSrc());
                }
            }
            this.MinhaConexao.Executa("delete from paginas where(id="+paginas.getID()+")");
            this.MinhaConexao.Executa("commit");
            return paginas;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir paginas."+erro.getMessage());}
    }
    public paginasBEAN salvar(paginasBEAN paginas,String path_upload)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((paginas.getIDStr()!=null)&&(buscarID(paginas).getIDStr()!=null)){
            return alterar(paginas,path_upload);
        }
        else{
            return inserir(paginas);
        }
    }
    public paginasBEAN buscarUltimo()throws Excecao{
       paginasBEAN ultimo = new paginasBEAN();
       try{ultimo= ((paginasBEAN)buscar(SELECT+" order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public paginasBEAN buscarPrimeiro()throws Excecao{
       paginasBEAN ultimo = new paginasBEAN();
       try{ultimo= ((paginasBEAN)buscar(SELECT+" order by titulo asc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public paginasBEAN buscarID(paginasBEAN paginas)throws Excecao{
       paginasBEAN registro = new paginasBEAN();
       try{registro= ((paginasBEAN)buscar(SELECT+"  where(id="+paginas.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECT);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:"+erro.getMessage());}
       return registros;

    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar(SELECT+"  where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:"+erro.getMessage());}
       return registros;
    }
    public Vector buscar(paginasBEAN paginas)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECT;
            String condicao="";
            if (paginas.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(titulo like'%"+paginas.getTitulo().replaceAll("'","''") +"%')";
            }       
            SQL=(condicao.length()>0)?SQL+" where"+condicao:SQL;        
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    } 
   public Vector buscar(String SQL)throws Excecao{
        Vector paginass=new Vector();
        try{
            if(SQL==null)
                SQL = SELECT;                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       paginasPOJO POJO = new  paginasPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("ID"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("Titulo"));                              
                       POJO.setSRC(MinhaConexao.MostrarCampoStr("src"));                              
                       paginasBEAN paginas_BEAN= new paginasBEAN(POJO);
                       paginass.add(paginas_BEAN);
                  }                                                  
            }
            return paginass;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar paginas! ERRO: "+ erro.getMessage());
        }
    }
}
