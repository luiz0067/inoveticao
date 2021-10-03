/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.paginasConteudoBEAN;
import POJO.paginasConteudoPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class paginasConteudoDAO {
    private Conexao MinhaConexao=null;
    public paginasConteudoDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public paginasConteudoBEAN salvar(paginasConteudoBEAN paginasConteudo,paginasConteudoBEAN paginasConteudoAntiga)throws Exception{        
        paginasConteudoBEAN resultado = buscarID(paginasConteudoAntiga);       
        if((paginasConteudoAntiga.getId_conteudoStr()!=null)&&(resultado.getId_conteudoStr()!=null)){
            resultado=alterar(paginasConteudo,paginasConteudoAntiga);
        }
        else{
           resultado=inserir(paginasConteudo); 
        }
        return resultado;
    }
    private paginasConteudoBEAN inserir(paginasConteudoBEAN paginasConteudo) throws Exception{
        String SQL="insert into paginasconteudo(id_conteudo,id_paginas) values("                   
            +paginasConteudo.getId_conteudoStr()
            +","
            +paginasConteudo.getId_paginasStr()
            + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar paginasConteudo.");
        }   
        return buscarUltimo();
    }
    private paginasConteudoBEAN alterar(paginasConteudoBEAN paginasConteudo,paginasConteudoBEAN paginasConteudoAntigo) throws Exception{
        String SQL="update paginasconteudo set "
        +"id_conteudo=" +paginasConteudo.getId_conteudoStr()+"," 
        +"id_paginas="+paginasConteudo.getId_paginasStr()+","
        +" where"
        + "(id_conteudo="+paginasConteudoAntigo.getId_conteudoStr()+")"
        + "and"
        + "(id_paginas="+paginasConteudoAntigo.getId_paginasStr()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar paginasConteudo.");
        }   
        return buscarID(paginasConteudo);
    }
    public paginasConteudoBEAN excluir(paginasConteudoBEAN paginasConteudo)throws Exception{
        String SQL="delete from paginasconteudo "
        +" where"
        + "(id_conteudo="+paginasConteudo.getId_conteudoStr()+")"
        + "and"
        + "(id_paginas="+paginasConteudo.getId_paginasStr()+")";
        try{
            paginasConteudo = buscarID(paginasConteudo);
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return paginasConteudo;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir paginasConteudo.");
        }
    }
    public paginasConteudoBEAN buscarID(paginasConteudoBEAN paginasConteudo)throws Excecao{
        paginasConteudoBEAN acc = new paginasConteudoBEAN();
        try{
            String SQL = "select id_conteudo,id_paginas from paginasconteudo "
            +" where"
            + "(id_conteudo="+paginasConteudo.getId_conteudoStr()+")"
            + "and"
            + "(id_paginas="+paginasConteudo.getId_paginasStr()+")";                 
                acc=(paginasConteudoBEAN)buscar(SQL).get(0); 
        }catch (Exception erro){}
        return acc;

    }   
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
            try{
                if(SQL==null)
                   SQL = "select id_conteudo,Id_paginas from paginasconteudo";                     
                    if(MinhaConexao.Busca(SQL)){
                          while(MinhaConexao.MoverProximo())
                          {
                               paginasConteudoPOJO POJO = new  paginasConteudoPOJO();
                               POJO.setId_paginas(MinhaConexao.MostrarCampoInteger("Id_paginas"));
                               POJO.setId_conteudo(MinhaConexao.MostrarCampoInteger("Id_conteudo"));
                               paginasConteudoBEAN paginasConteudo_BEAN = new paginasConteudoBEAN(POJO);
                               registros.add(paginasConteudo_BEAN);
                          }                     
                              
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar paginasConteudo! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar("SELECT id_conteudo,id_paginas from paginasconteudo");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar paginasConteudo! ERRO:");
       }
     }
    public Vector buscarId_conteudo(String Id_conteudo)throws Excecao{
       try{ 
         String SQL="select id_conteudo,id_paginas from paginasconteudo where(id_conteudo like'%"+Id_conteudo.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar paginasConteudo! ERRO:");
       }
     }
    public Vector buscar(paginasConteudoBEAN paginasConteudo)throws Excecao{
        try{
        String SQL="SELECT id_conteudo,id_paginas from paginasconteudo";
        String condicao="";
        if (paginasConteudo.getId_conteudoStr()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(id_conteudo ="+paginasConteudo.getId_conteudoStr()+")";
        }
        if(paginasConteudo.getId_paginasStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(id_paginas ="+paginasConteudo.getId_paginasStr() +")";
        }
        SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL; 
        return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar paginasConteudo! ERRO:");
       }
     }
    public paginasConteudoBEAN buscarUltimo(){
       paginasConteudoBEAN acc = new paginasConteudoBEAN();
        try{ 
         String SQL="select id_conteudo,id_paginas from paginasconteudo order by id_conteudo desc, id_paginas desc ";
         
         return ((paginasConteudoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
}
