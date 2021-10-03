/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.garota_conteudoBEAN;
import POJO.garota_conteudoPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Date;
import java.util.Vector;

public class garota_conteudoDAO {
    private Conexao MinhaConexao=null;
    private static final String SELECT = "SELECT garota_conteudo.id,garota_conteudo.id_garotapg,garotapg.titulo as titulo_pagina,garota_conteudo.titulo,garota_conteudo.inicio,garota_conteudo.garota_conteudo,garota_conteudo.fim from garota_conteudo left join garotapg on(garota_conteudo.id_garotapg=garotapg.id) ";
    public garota_conteudoDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public garota_conteudoBEAN salvar(garota_conteudoBEAN garota_conteudo)throws Exception{
        if(garota_conteudo.getidStr()!=null){
            garota_conteudo.setfim(new Date());
            garota_conteudo=alterar(garota_conteudo);
        }
        else{
            garota_conteudo.setinicio(new Date());
            garota_conteudo=inserir(garota_conteudo);
        }
        return garota_conteudo;
    }
    private garota_conteudoBEAN inserir(garota_conteudoBEAN garota_conteudo) throws Exception{
        String SQL="insert into garota_conteudo(id_garotapg,titulo,inicio,garota_conteudo,fim) values("                   
            +garota_conteudo.getid_garotapgStr()+","
            +Conexao.sqlProtection(garota_conteudo.gettitulo())+","
            +Conexao.sqlProtection(garota_conteudo.getinicioStr("yyyy-MM-dd HH:mm:ss"))+","
            +Conexao.sqlProtection(garota_conteudo.getgarota_conteudo())+","
            +Conexao.sqlProtection(garota_conteudo.getfimStr("yyyy-MM-dd HH:mm:ss")) +""
            + ")";
        try{
            if(!this.MinhaConexao.Executa(SQL))
                return new garota_conteudoBEAN();
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar garota_conteudo.");
        }   
        return buscarUltimo();
    }
    private garota_conteudoBEAN alterar(garota_conteudoBEAN garota_conteudo) throws Exception{
        String SQL="update garota_conteudo set "
        +"id_garotapg=" +garota_conteudo.getid_garotapgStr()+","         
        +"titulo=" +Conexao.sqlProtection(garota_conteudo.gettitulo())+"," 
        +"inicio="+Conexao.sqlProtection(garota_conteudo.getinicioStr("yyyy-MM-dd HH:mm:ss"))+","
        +"garota_conteudo="+Conexao.sqlProtection(garota_conteudo.getgarota_conteudo())+","
        +"fim="+Conexao.sqlProtection(garota_conteudo.getfimStr("yyyy-MM-dd HH:mm:ss")) +" "
        +" where"
        + "(id="+garota_conteudo.getid()+")"
        + "and"
        + "(id_garotapg="+garota_conteudo.getid_garotapgStr()+")"
        + ";";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar garota_conteudo.");
        }   
        return buscarid(garota_conteudo);
    }
    public garota_conteudoBEAN excluir(garota_conteudoBEAN garota_conteudo)throws Exception{
        String SQL="delete from garota_conteudo "
        +" where"
        + "(id="+garota_conteudo.getid()+")"
        + ";";
        try{
            garota_conteudo=buscarid(garota_conteudo);
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return garota_conteudo;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir garota_conteudo.");
        }
    }
    public garota_conteudoBEAN buscarid(garota_conteudoBEAN garota_conteudo)throws Excecao{
        garota_conteudoBEAN acc = new garota_conteudoBEAN();
        try{
            String SQL = SELECT
            +" where"
            + "(garota_conteudo.id="+garota_conteudo.getid()+")"
            + ";";                  
            acc=(garota_conteudoBEAN)buscar(SQL).get(0); 
        }catch (Exception erro){}
        return acc;

    } 
    public Vector buscar(garota_conteudoBEAN garota_conteudo)throws Excecao{
        try{
        String SQL=SELECT;
        String condicao="";
        if ((garota_conteudo.gettitulo()!=null)&&(garota_conteudo.gettitulo().length()!=0)){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(titulo like'%"+garota_conteudo.gettitulo().replaceAll("'","''") +"%')";
        }
        if(garota_conteudo.getinicio() !=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(inicio = "+Conexao.sqlProtection(garota_conteudo.getinicioStr("yyyy-MM-dd"))+")";
        }
        if(garota_conteudo.getfim()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(fim = "+Conexao.sqlProtection(garota_conteudo.getfimStr("yyyy-MM-dd"))+")";
        }
        if((garota_conteudo.getgarota_conteudo()!=null)&&(garota_conteudo.getgarota_conteudo().length()!=0)){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(garota_conteudo like'%"+garota_conteudo.getgarota_conteudo().replaceAll("'","''") +"%')";
         }
        if(garota_conteudo.getid_garotapgStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(id_garotapg ="+garota_conteudo.getid_garotapgStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar garota_conteudo! ERRO:");
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
                               garota_conteudoPOJO POJO = new  garota_conteudoPOJO();
                               POJO.setid(MinhaConexao.MostrarCampoInteger("id"));
                               POJO.setid_garotapg(MinhaConexao.MostrarCampoInteger("id_garotapg"));
                               POJO.settitulo(MinhaConexao.MostrarCampoStr("titulo"));
                               POJO.setinicio(MinhaConexao.MostrarCampoDate("inicio"));
                               POJO.setgarota_conteudo(MinhaConexao.MostrarCampoStr("garota_conteudo"));
                               POJO.setfim(MinhaConexao.MostrarCampoDate("fim"));
                               garota_conteudoBEAN garota_conteudo_BEAN = new garota_conteudoBEAN(POJO);
                               registros.add(garota_conteudo_BEAN);
                          }                     
                              
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar garota_conteudo! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar garota_conteudo! ERRO:");
       }
    }
    public Vector buscartitulo(String titulo)throws Excecao{
       try{ 
     String SQL=SELECT+" where(titulo like'%"+titulo.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar garota_conteudo! ERRO:");
       }
    }
    public garota_conteudoBEAN buscarUltimo(){
       garota_conteudoBEAN acc = new garota_conteudoBEAN();
        try{ 
         String SQL=SELECT+" order by id desc";
         
         return ((garota_conteudoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
    public garota_conteudoBEAN buscarUltimaPagina(garota_conteudoBEAN garota_conteudo){
       garota_conteudoBEAN acc = new garota_conteudoBEAN();
        try{ 
         String SQL=SELECT+" where(id_garotapg="+garota_conteudo.getid_garotapgStr()+") order by id desc";
         
         return ((garota_conteudoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
     public Vector buscarUltimasgarotapg(garota_conteudoBEAN garota_conteudo)throws Excecao{
        try{
            String SQL="";
            if (garota_conteudo.getid_garotapgStr() ==null){
                garota_conteudo.setid_garotapg((new garotapgDAO(MinhaConexao)).buscarPrimeiro().getidStr());
            }
            if (garota_conteudo.getid_garotapgStr()!=null){
                String id=garota_conteudo.getid_garotapgStr();
                SQL=SELECT+"  where(id_garotapg="+id+");";
            }
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }    
}
