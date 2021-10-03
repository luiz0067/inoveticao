/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.patrocinadoresBEAN;
import BEAN.categoriaPatrocinadoresBEAN;
import BEAN.patrocinadoresBEAN;
import POJO.patrocinadoresPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class patrocinadoresDAO {
    private Conexao MinhaConexao=null;
    private static final String SELECT=" select distinct patrocinadores.id,patrocinadores.titulo,patrocinadores.descricao,patrocinadores.src, patrocinadores.id_categoria, categoriapatrocinadores.titulo as titulo_categoria from patrocinadores left join categoriapatrocinadores on(patrocinadores.id_categoria=categoriapatrocinadores.id) ";
    public patrocinadoresDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public patrocinadoresBEAN salvar(patrocinadoresBEAN patrocinadores,String path_upload)throws Exception{        
        patrocinadoresBEAN resultado = buscarID(patrocinadores);       
        if((patrocinadores.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            if (resultado.getSrc()!=null){
                functions.deletaImagensRedimencionadas(path_upload,resultado.getSrc());
            }
            resultado=alterar(patrocinadores,path_upload);
        }
        else{
           resultado=inserir(patrocinadores); 
        }
        return resultado;
    }
        
    
    public patrocinadoresBEAN inserir(patrocinadoresBEAN patrocinadores) throws Exception{
        String SQL=" insert into patrocinadores(titulo,descricao,src,id_Categoria) values("                   
            +Conexao.sqlProtection(patrocinadores.getTitulo())+","
            +Conexao.sqlProtection(patrocinadores.getDescricao())+","
            +Conexao.sqlProtection(patrocinadores.getSrc())+","
            +patrocinadores.getId_CategoriaStr()+""
            + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar patrocinadores.");
        }   
        return buscarUltimo();
    }
    private patrocinadoresBEAN alterar(patrocinadoresBEAN patrocinadores,String path_upload) throws Exception{
        String SQL=" update patrocinadores set ";
        SQL+="titulo=" +Conexao.sqlProtection(patrocinadores.getTitulo())+"," ;
        SQL+="descricao="+Conexao.sqlProtection(patrocinadores.getDescricao())+" ";
        if((patrocinadores.getSrc()!=null)&&(patrocinadores.getSrc().length()!=0)){
            SQL+=",";
            SQL+="Src="+Conexao.sqlProtection(patrocinadores.getSrc())+" ";
        }
        SQL+=",";
        SQL+="id_categoria="+patrocinadores.getId_CategoriaStr()+" ";
        SQL+=" where(id="+patrocinadores.getIDStr()+")";
        try{
            patrocinadores=buscarID(patrocinadores);
            if(patrocinadores.getIDStr()!=null){
                if (patrocinadores.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,patrocinadores.getSrc());
                }
            }  
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar patrocinadores.");
        }   
        return buscarID(patrocinadores);
    }
    public patrocinadoresBEAN excluir(patrocinadoresBEAN patrocinadores,String path_upload)throws Exception{
        String SQL=" delete from patrocinadores where(id="+patrocinadores.getIDStr()+")";
        try{
            patrocinadores=buscarID(patrocinadores);
            if(patrocinadores.getIDStr()!=null){
                if (patrocinadores.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,patrocinadores.getSrc());
                }
            }            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return patrocinadores;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir patrocinadores.");
        }
    }
    public patrocinadoresBEAN buscarID(patrocinadoresBEAN patrocinadores)throws Excecao{
        patrocinadoresBEAN acc = new patrocinadoresBEAN();
        try{
            String SQL = SELECT+" where(patrocinadores.id="+patrocinadores.getIDStr()+");";                   
            acc=(patrocinadoresBEAN)buscar(SQL).get(0); 
        }catch (Exception erro){}
        return acc;

    }   
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
            try{
                if(SQL==null)
                   SQL = SELECT;                     
                    if(MinhaConexao.Busca(SQL)){
                          while(MinhaConexao.MoverProximo())
                          {
                               patrocinadoresPOJO POJO = new  patrocinadoresPOJO();
                               POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                               POJO.setId_Categoria(MinhaConexao.MostrarCampoInteger("id_Categoria"));
                               POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                               POJO.setTitulo_Categoria(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                               POJO.setDescricao(MinhaConexao.MostrarCampoStr("Descricao"));
                               POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                               patrocinadoresBEAN patrocinadores_BEAN = new patrocinadoresBEAN(POJO);
                               registros.add(patrocinadores_BEAN);
                          }                     
                              
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar patrocinadores! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
     }
    
    public Vector buscarOrdenada()throws Excecao{
       try{ 
         return buscar(SELECT+" order by titulo_categoria asc, id desc");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
     }
    
    public Vector buscar(patrocinadoresBEAN patrocinadores)throws Excecao{
       try{
        String SQL=SELECT;
        String condicao="";
        if (patrocinadores.getTitulo()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(patrocinadores.titulo like'%"+patrocinadores.getTitulo().replaceAll("'","''") +"%')";
        }
        if(patrocinadores.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(patrocinadores.descricao like'%"+patrocinadores.getDescricao().replaceAll("'","''") +"%')";
        }
        if(patrocinadores.getSrc()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(patrocinadores.src like'%"+patrocinadores.getSrc().replaceAll("'","''") +"%')";
        }
        if(patrocinadores.getId_CategoriaStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(patrocinadores.id_categoria ="+patrocinadores.getId_CategoriaStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
     }
    public Vector buscartitulo(patrocinadoresBEAN patrocinadores)throws Excecao{
       try{ 
         String SQL=SELECT+" where(patrocinadores.titulo like'%"+patrocinadores.getTitulo().replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
     }
    public Vector buscarPorCategoria(patrocinadoresBEAN patrocinadores)throws Excecao{
        try{
            String SQL="";
            if (
                    (patrocinadores.getId_CategoriaStr()==null)
                    ||
                    (
                        (patrocinadores.getId_CategoriaStr()!=null)
                         &&
                        (patrocinadores.getId_Categoria()==0)
                    )
            )
            {
                String id=(new categoriaPatrocinadoresDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                patrocinadores.setId_Categoria(id);
            }
            if (patrocinadores.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(patrocinadores.id_categoria="+patrocinadores.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by patrocinadores.id desc ";
            }
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
    }
    public Vector buscarPorCategoria(patrocinadoresBEAN patrocinadores,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (patrocinadores.getId_CategoriaStr()==null)
                    ||
                    (
                        (patrocinadores.getId_CategoriaStr()!=null)
                         &&
                        (patrocinadores.getId_Categoria()==0)
                    )
            )
            {
                String id=(new categoriaPatrocinadoresDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                patrocinadores.setId_Categoria(id);
            }
            if (patrocinadores.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(patrocinadores.id_categoria="+patrocinadores.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by patrocinadores.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaPatrocinadoresBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            patrocinadoresBEAN patrocinadores=new patrocinadoresBEAN();
            if (
                    (categoria.getIDStr()==null)
                    ||
                    (
                        (categoria.getIDStr()!=null)
                         &&
                        (categoria.getID()==0)
                    )
            )
            {
                String id=(new categoriaPatrocinadoresDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                
                patrocinadores.setId_Categoria(id);
            }
            if (patrocinadores.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(patrocinadores.id_categoria="+patrocinadores.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by patrocinadores.Id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaPatrocinadoresBEAN categoria)throws Excecao{
        try{
            String SQL="";
            patrocinadoresBEAN patrocinadores=new patrocinadoresBEAN();
            if (
                    (categoria.getIDStr()==null)
                    ||
                    (
                        (categoria.getIDStr()!=null)
                         &&
                        (categoria.getID()==0)
                    )
            )
            {
                String id=(new categoriaPatrocinadoresDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                patrocinadores.setId_Categoria(id);
            }
            if (patrocinadores.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(patrocinadores.id_categoria="+patrocinadores.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by patrocinadores.Id desc ;";
            }
         
            return buscar(SQL);
        }
        catch(Exception erro){
           throw new Excecao("Erro ao buscar patrocinadores! ERRO:");
        }
    }
    public patrocinadoresBEAN buscarUltimo(){
       patrocinadoresBEAN acc = new patrocinadoresBEAN();
        try{ 
         String SQL=" select distinct id,titulo,descricao,src, id_categoria from patrocinadores order by id desc ";
         
         return ((patrocinadoresBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
}
