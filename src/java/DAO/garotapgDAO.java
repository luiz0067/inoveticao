/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.garotapgBEAN;
import POJO.garotapgPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class garotapgDAO {
    private Conexao MinhaConexao=null;
    private String SELECT="select id,titulo,src from garotapg ";
    public garotapgDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }    
    private garotapgBEAN inserir(garotapgBEAN garotapg)throws Exception{
        try{
            
            String SQL="insert into garotapg(titulo,src) values("
                    + ""+Conexao.sqlProtection(garotapg.gettitulo())+""
                    + ","
                    + ""+Conexao.sqlProtection(garotapg.getsrc())+""
                    + ")";
            if (garotapg.getsrc()==null){
                SQL="insert into garotapg(titulo) values("
                    + ""+Conexao.sqlProtection(garotapg.gettitulo())+""
                    
                    + ")";
            }
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return buscarUltimo();
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar garotapg."+erro.getMessage());}    
    }    
    private garotapgBEAN alterar(garotapgBEAN garotapg,String path_upload)throws Exception{
        try{
            String SQL="update garotapg set ";
                    SQL+= "titulo=" +Conexao.sqlProtection(garotapg.gettitulo()) +"";
                    if(garotapg.getsrc()!=null){
                        SQL+= ",";
                        SQL+= "src="+Conexao.sqlProtection(garotapg.getsrc())+ "";
                    }
                    SQL+= " where(id="+garotapg.getid()+")";
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            garotapg=buscarid(garotapg);
            if(garotapg.getidStr()!=null){
                if (garotapg.getsrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,garotapg.getsrc());
                }
            }               
            return buscarid(garotapg);
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da garotapg."+erro.getMessage());}          
    }
    public garotapgBEAN excluir(garotapgBEAN garotapg,String path_upload)throws Exception{
        try{
            garotapg=buscarid(garotapg);
            if(garotapg.getidStr()!=null){
                if (garotapg.getsrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,garotapg.getsrc());
                }
            }
            this.MinhaConexao.Executa("delete from garotapg where(id="+garotapg.getid()+")");
            this.MinhaConexao.Executa("commit");
            return garotapg;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir garotapg."+erro.getMessage());}
    }
    public garotapgBEAN salvar(garotapgBEAN garotapg,String path_upload)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((garotapg.getidStr()!=null)&&(buscarid(garotapg).getidStr()!=null)){
            return alterar(garotapg,path_upload);
        }
        else{
            return inserir(garotapg);
        }
    }
    public garotapgBEAN buscarUltimo()throws Excecao{
       garotapgBEAN ultimo = new garotapgBEAN();
       try{ultimo= ((garotapgBEAN)buscar(SELECT+" order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public garotapgBEAN buscarPrimeiro()throws Excecao{
       garotapgBEAN ultimo = new garotapgBEAN();
       try{ultimo= ((garotapgBEAN)buscar(SELECT+" order by titulo asc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public garotapgBEAN buscarid(garotapgBEAN garotapg)throws Excecao{
       garotapgBEAN registro = new garotapgBEAN();
       try{registro= ((garotapgBEAN)buscar(SELECT+"  where(id="+garotapg.getidStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar(SELECT);}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:"+erro.getMessage());}
       return registros;

    }
    public Vector buscartitulo(String titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar(SELECT+"  where(titulo like'%"+titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar titulo ERRO:"+erro.getMessage());}
       return registros;
    }
    public Vector buscar(garotapgBEAN garotapg)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL=SELECT;
            String condicao="";
            if (garotapg.gettitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(titulo like'%"+garotapg.gettitulo().replaceAll("'","''") +"%')";
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
        Vector garotapgs=new Vector();
        try{
            if(SQL==null)
                SQL = SELECT;                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       garotapgPOJO POJO = new  garotapgPOJO();
                       POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.settitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));                              
                       garotapgBEAN garotapg_BEAN= new garotapgBEAN(POJO);
                       garotapgs.add(garotapg_BEAN);
                  }                                                  
            }
            return garotapgs;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar garotapg! ERRO: "+ erro.getMessage());
        }
    }
}
