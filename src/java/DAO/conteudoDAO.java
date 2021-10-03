/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.conteudoBEAN;
import POJO.conteudoPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Date;
import java.util.Vector;

public class conteudoDAO {
    private Conexao MinhaConexao=null;
    private static final String SELECT = "SELECT conteudo.id,conteudo.id_paginas,paginas.titulo as titulo_pagina,conteudo.titulo,conteudo.inicio,conteudo.conteudo,conteudo.fim from conteudo left join paginas on(conteudo.Id_paginas=paginas.id) ";
    public conteudoDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public conteudoBEAN salvar(conteudoBEAN conteudo)throws Exception{
        if(conteudo.getIDStr()!=null){
            conteudo.setFim(new Date());
            conteudo=alterar(conteudo);
        }
        else{
            conteudo.setInicio(new Date());
            conteudo=inserir(conteudo);
        }
        return conteudo;
    }
    private conteudoBEAN inserir(conteudoBEAN conteudo) throws Exception{
        String SQL="insert into conteudo(id_paginas,titulo,inicio,conteudo,fim) values("                   
            +conteudo.getId_paginasStr()+","
            +Conexao.sqlProtection(conteudo.getTitulo())+","
            +Conexao.sqlProtection(conteudo.getInicioStr("yyyy-MM-dd HH:mm:ss"))+","
            +Conexao.sqlProtection(conteudo.getConteudo())+","
            +Conexao.sqlProtection(conteudo.getFimStr("yyyy-MM-dd HH:mm:ss")) +""
            + ")";
        try{
            if(!this.MinhaConexao.Executa(SQL))
                return new conteudoBEAN();
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Conteudo.");
        }   
        return buscarUltimo();
    }
    private conteudoBEAN alterar(conteudoBEAN conteudo) throws Exception{
        String SQL="update conteudo set "
        +"id_paginas=" +conteudo.getId_paginasStr()+","         
        +"titulo=" +Conexao.sqlProtection(conteudo.getTitulo())+"," 
        +"inicio="+Conexao.sqlProtection(conteudo.getInicioStr("yyyy-MM-dd HH:mm:ss"))+","
        +"conteudo="+Conexao.sqlProtection(conteudo.getConteudo())+","
        +"fim="+Conexao.sqlProtection(conteudo.getFimStr("yyyy-MM-dd HH:mm:ss")) +" "
        +" where"
        + "(id="+conteudo.getID()+")"
        + "and"
        + "(id_paginas="+conteudo.getId_paginasStr()+")"
        + ";";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Conteudo.");
        }   
        return buscarID(conteudo);
    }
    public conteudoBEAN excluir(conteudoBEAN conteudo)throws Exception{
        String SQL="delete from conteudo "
        +" where"
        + "(id="+conteudo.getID()+")"
        + ";";
        try{
            conteudo=buscarID(conteudo);
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return conteudo;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir Conteudo.");
        }
    }
    public conteudoBEAN buscarID(conteudoBEAN conteudo)throws Excecao{
        conteudoBEAN acc = new conteudoBEAN();
        try{
            String SQL = SELECT
            +" where"
            + "(conteudo.id="+conteudo.getID()+")"
            + ";";                  
            acc=(conteudoBEAN)buscar(SQL).get(0); 
        }catch (Exception erro){}
        return acc;

    } 
    public Vector buscar(conteudoBEAN conteudo)throws Excecao{
        try{
        String SQL=SELECT;
        String condicao="";
        if ((conteudo.getTitulo()!=null)&&(conteudo.getTitulo().length()!=0)){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(titulo like'%"+conteudo.getTitulo().replaceAll("'","''") +"%')";
        }
        if(conteudo.getInicio() !=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(inicio = "+Conexao.sqlProtection(conteudo.getInicioStr("yyyy-MM-dd"))+")";
        }
        if(conteudo.getFim()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(fim = "+Conexao.sqlProtection(conteudo.getFimStr("yyyy-MM-dd"))+")";
        }
        if((conteudo.getConteudo()!=null)&&(conteudo.getConteudo().length()!=0)){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(conteudo like'%"+conteudo.getConteudo().replaceAll("'","''") +"%')";
         }
        if(conteudo.getId_paginasStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(id_paginas ="+conteudo.getId_paginasStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Conteudo! ERRO:");
       }
     }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
            try{
                if(SQL==null)
                   SQL = SELECT;                     
                    if(MinhaConexao.Busca(SQL)){
                          while(MinhaConexao.MoverProximo())
                          {
                               conteudoPOJO POJO = new  conteudoPOJO();
                               POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                               POJO.setId_Paginas(MinhaConexao.MostrarCampoInteger("id_Paginas"));
                               POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                               POJO.setInicio(MinhaConexao.MostrarCampoDate("Inicio"));
                               POJO.setConteudo(MinhaConexao.MostrarCampoStr("conteudo"));
                               POJO.setFim(MinhaConexao.MostrarCampoDate("fim"));
                               conteudoBEAN conteudo_BEAN = new conteudoBEAN(POJO);
                               registros.add(conteudo_BEAN);
                          }                     
                              
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar Conteudo! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Conteudo! ERRO:");
       }
    }
    public Vector buscartitulo(String titulo)throws Excecao{
       try{ 
     String SQL=SELECT+" where(titulo like'%"+titulo.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Conteudo! ERRO:");
       }
    }
    public conteudoBEAN buscarUltimo(){
       conteudoBEAN acc = new conteudoBEAN();
        try{ 
         String SQL=SELECT+" order by id desc";
         
         return ((conteudoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
    public conteudoBEAN buscarUltimaPagina(conteudoBEAN conteudo){
       conteudoBEAN acc = new conteudoBEAN();
        try{ 
         String SQL=SELECT+" where(Id_paiinas="+conteudo.getId_paginasStr()+") order by id desc";
         
         return ((conteudoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
     public Vector buscarUltimasPaginas(conteudoBEAN conteudo)throws Excecao{
        try{
            String SQL="";
            if (conteudo.getId_paginasStr() ==null){
                conteudo.setId_paginas((new paginasDAO(MinhaConexao)).buscarPrimeiro().getIDStr());
            }
            if (conteudo.getId_paginasStr()!=null){
                String id=conteudo.getId_paginasStr();
                SQL=SELECT+"  where(id_paginas="+id+");";
            }
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }    
}
