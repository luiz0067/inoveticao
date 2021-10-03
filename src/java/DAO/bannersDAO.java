/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.categoriaBannersBEAN;
import BEAN.bannersBEAN;
import POJO.bannersPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class bannersDAO {
    private Conexao MinhaConexao=null;
    private static final String SELECT=" select distinct banners.id,banners.titulo,banners.descricao,banners.src, banners.id_categoria, categoriabanners.titulo as titulo_categoria from banners left join categoriabanners on(banners.id_categoria=categoriabanners.id) ";
    public bannersDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public bannersBEAN salvar(bannersBEAN banners,String path_upload)throws Exception{        
        bannersBEAN resultado = buscarID(banners);       
        if((banners.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            if (resultado.getSrc()!=null){
                functions.deletaImagensRedimencionadas(path_upload,resultado.getSrc());
            }
            resultado=alterar(banners,path_upload);
        }
        else{
           resultado=inserir(banners); 
        }
        return resultado;
    }
        
    
    public bannersBEAN inserir(bannersBEAN banners) throws Exception{
        String SQL=" insert into banners(titulo,descricao,src,id_Categoria) values("                   
            +Conexao.sqlProtection(banners.getTitulo())+","
            +Conexao.sqlProtection(banners.getDescricao())+","
            +Conexao.sqlProtection(banners.getSrc())+","
            +banners.getId_CategoriaStr()+""
            + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar banners.");
        }   
        return buscarUltimo();
    }
    private bannersBEAN alterar(bannersBEAN banners,String path_upload) throws Exception{
        String SQL=" update banners set ";
        SQL+="titulo=" +Conexao.sqlProtection(banners.getTitulo())+"," ;
        SQL+="descricao="+Conexao.sqlProtection(banners.getDescricao())+" ";
        if((banners.getSrc()!=null)&&(banners.getSrc().length()!=0)){
            SQL+=",";
            SQL+="Src="+Conexao.sqlProtection(banners.getSrc())+" ";
        }
        SQL+=",";
        SQL+="id_categoria="+banners.getId_CategoriaStr()+" ";
        SQL+=" where(id="+banners.getIDStr()+")";
        try{
            banners=buscarID(banners);
            if(banners.getIDStr()!=null){
                if (banners.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,banners.getSrc());
                }
            }  
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar banners.");
        }   
        return buscarID(banners);
    }
    public bannersBEAN excluir(bannersBEAN banners,String path_upload)throws Exception{
        String SQL=" delete from banners where(id="+banners.getIDStr()+")";
        try{
            banners=buscarID(banners);
            if(banners.getIDStr()!=null){
                if (banners.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,banners.getSrc());
                }
            }            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return banners;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir banners.");
        }
    }
    public bannersBEAN buscarID(bannersBEAN banners)throws Excecao{
        bannersBEAN acc = new bannersBEAN();
        try{
            String SQL = SELECT+" where(banners.id="+banners.getIDStr()+");";                   
            acc=(bannersBEAN)buscar(SQL).get(0); 
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
                               bannersPOJO POJO = new  bannersPOJO();
                               POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                               POJO.setId_Categoria(MinhaConexao.MostrarCampoInteger("id_Categoria"));
                               POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                               POJO.setTitulo_Categoria(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                               POJO.setDescricao(MinhaConexao.MostrarCampoStr("Descricao"));
                               POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                               bannersBEAN banners_BEAN = new bannersBEAN(POJO);
                               registros.add(banners_BEAN);
                          }                     
                              
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banners! ERRO: ");
        }
    }
    public Vector buscarTodosPares()throws Excecao{
        int i=1;
        Vector registros=new Vector();
        try{
            String SQL = SELECT;                     
            if(MinhaConexao.Busca(SQL)){
                while(MinhaConexao.MoverProximo())
                {
                    if(i%2==0){
                        bannersPOJO POJO = new  bannersPOJO();
                        POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                        POJO.setId_Categoria(MinhaConexao.MostrarCampoInteger("id_Categoria"));
                        POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                        POJO.setTitulo_Categoria(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                        POJO.setDescricao(MinhaConexao.MostrarCampoStr("Descricao"));
                        POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                        bannersBEAN banners_BEAN = new bannersBEAN(POJO);
                        registros.add(banners_BEAN);
                    }
                    i++;
                }
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banners! ERRO: ");
        }
    }
    public Vector buscarTodosImpares()throws Excecao{
        int i=1;
        Vector registros=new Vector();
        try{
            String SQL = SELECT;                     
            if(MinhaConexao.Busca(SQL)){
                while(MinhaConexao.MoverProximo())
                {
                    if(i%2!=0){
                        bannersPOJO POJO = new  bannersPOJO();
                        POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                        POJO.setId_Categoria(MinhaConexao.MostrarCampoInteger("id_Categoria"));
                        POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                        POJO.setTitulo_Categoria(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                        POJO.setDescricao(MinhaConexao.MostrarCampoStr("Descricao"));
                        POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                        bannersBEAN banners_BEAN = new bannersBEAN(POJO);
                        registros.add(banners_BEAN);
                    }
                    i++;
                }
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banners! ERRO: ");
        }
    }    
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
     }
    
    public Vector buscarOrdenada()throws Excecao{
       try{ 
         return buscar(SELECT+" order by titulo_categoria asc, id desc");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
     }
    
    public Vector buscar(bannersBEAN banners)throws Excecao{
       try{
        String SQL=SELECT;
        String condicao="";
        if (banners.getTitulo()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(banners.titulo like'%"+banners.getTitulo().replaceAll("'","''") +"%')";
        }
        if(banners.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(banners.descricao like'%"+banners.getDescricao().replaceAll("'","''") +"%')";
        }
        if(banners.getSrc()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(banners.src like'%"+banners.getSrc().replaceAll("'","''") +"%')";
        }
        if(banners.getId_CategoriaStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(banners.id_categoria ="+banners.getId_CategoriaStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
     }
    public Vector buscartitulo(bannersBEAN banners)throws Excecao{
       try{ 
         String SQL=SELECT+" where(banners.titulo like'%"+banners.getTitulo().replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
     }
    public Vector buscarPorCategoria(bannersBEAN banners)throws Excecao{
        try{
            String SQL="";
            if (
                    (banners.getId_CategoriaStr()==null)
                    ||
                    (
                        (banners.getId_CategoriaStr()!=null)
                         &&
                        (banners.getId_Categoria()==0)
                    )
            )
            {
                String id=(new categoriaBannersDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                banners.setId_Categoria(id);
            }
            if (banners.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(banners.id_categoria="+banners.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by banners.id desc ";
            }
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
    }
    public Vector buscarPorCategoria(bannersBEAN banners,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (banners.getId_CategoriaStr()==null)
                    ||
                    (
                        (banners.getId_CategoriaStr()!=null)
                         &&
                        (banners.getId_Categoria()==0)
                    )
            )
            {
                String id=(new categoriaBannersDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                banners.setId_Categoria(id);
            }
            if (banners.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(banners.id_categoria="+banners.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by banners.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaBannersBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            bannersBEAN banners=new bannersBEAN();
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
                String id=(new categoriaBannersDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                
                banners.setId_Categoria(id);
            }
            if (banners.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(banners.id_categoria="+banners.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by banners.Id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaBannersBEAN categoria)throws Excecao{
        try{
            String SQL="";
            bannersBEAN banners=new bannersBEAN();
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
                String id=(new categoriaBannersDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                banners.setId_Categoria(id);
            }
            if (banners.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(banners.id_categoria="+banners.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by banners.Id desc ;";
            }
         
            return buscar(SQL);
        }
        catch(Exception erro){
           throw new Excecao("Erro ao buscar banners! ERRO:");
        }
    }
    public bannersBEAN buscarUltimo(){
       bannersBEAN acc = new bannersBEAN();
        try{ 
         String SQL=" select distinct id,titulo,descricao,src, id_categoria from banners order by id desc ";
         
         return ((bannersBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
}
