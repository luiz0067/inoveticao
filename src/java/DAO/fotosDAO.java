/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import BEAN.categoriaFotosBEAN;
import BEAN.fotosBEAN;
import POJO.fotosPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class fotosDAO {
    private Conexao MinhaConexao=null;
    private static final String SELECT=" select distinct fotos.id,fotos.titulo,fotos.descricao,fotos.src, fotos.id_categoria, categoriafotos.titulo as titulo_categoria from fotos left join categoriafotos on(fotos.id_categoria=categoriafotos.id) ";
    public fotosDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public fotosBEAN salvar(fotosBEAN fotos,String path_upload)throws Exception{        
        fotosBEAN resultado = buscarID(fotos);       
        if((fotos.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            resultado=alterar(fotos,path_upload);
        }
        else{
           resultado=inserir(fotos); 
        }
        return resultado;
    }
        
    
    private fotosBEAN inserir(fotosBEAN fotos) throws Exception{
        String SQL=" insert into fotos(titulo,descricao,src,id_Categoria) values("                   
            +Conexao.sqlProtection(fotos.getTitulo())+","
            +Conexao.sqlProtection(fotos.getDescricao())+","
            +Conexao.sqlProtection(fotos.getSrc())+","
            +fotos.getId_CategoriaStr()+""
            + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar foto.");
        }   
        return buscarUltimo();
    }
    private fotosBEAN alterar(fotosBEAN fotos,String path_upload) throws Exception{
        try{
        String SQL=" update fotos set ";
        SQL+="titulo=" +Conexao.sqlProtection(fotos.getTitulo())+"," ;
        SQL+="descricao="+Conexao.sqlProtection(fotos.getDescricao())+" ";
        if((fotos.getSrc()!=null)&&(fotos.getSrc().length()!=0)){
            SQL+=",";
            SQL+="src="+Conexao.sqlProtection(fotos.getSrc())+" ";
            fotosBEAN fotos1=buscarID(fotos);
            if((fotos1.getIDStr()!=null)&&((fotos1.getSrc()!=null)&&(fotos1.getSrc().length()!=0))){
                if (fotos1.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,fotos1.getSrc());
                }
            }  
        }
        SQL+=",";
        SQL+="id_categoria="+fotos.getId_CategoriaStr()+" ";
        SQL+=" where(id="+fotos.getIDStr()+")";            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar foto.");
        }   
        return buscarID(fotos);
    }
    public fotosBEAN excluir(fotosBEAN fotos,String path_upload)throws Exception{
        String SQL=" delete from fotos where(id="+fotos.getIDStr()+")";
        try{
            fotos=buscarID(fotos);
            if(fotos.getIDStr()!=null){
                if (fotos.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,fotos.getSrc());
                }
            }            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return fotos;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir foto.");
        }
    }
    public fotosBEAN buscarID(fotosBEAN foto)throws Excecao{
        fotosBEAN acc = new fotosBEAN();
        try{
            String SQL = SELECT+" where(fotos.id="+foto.getIDStr()+");";                   
            acc=(fotosBEAN)buscar(SQL).get(0); 
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
                               fotosPOJO POJO = new  fotosPOJO();
                               POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                               POJO.setId_Categoria(MinhaConexao.MostrarCampoInteger("id_Categoria"));
                               POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                               POJO.setTitulo_Categoria(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                               POJO.setDescricao(MinhaConexao.MostrarCampoStr("Descricao"));
                               POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                               fotosBEAN fotos_BEAN = new fotosBEAN(POJO);
                               registros.add(fotos_BEAN);
                          }                     
                              
            }
            return registros;
       }    
          catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar foto! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
    
    public Vector buscarOrdenada()throws Excecao{
       try{ 
         return buscar(SELECT+" order by titulo_categoria asc, id desc");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
    
    public Vector buscar(fotosBEAN foto)throws Excecao{
       try{
        String SQL=SELECT;
        String condicao="";
        if (foto.getTitulo()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(fotos.titulo like'%"+foto.getTitulo().replaceAll("'","''") +"%')";
        }
        if(foto.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(fotos.descricao like'%"+foto.getDescricao().replaceAll("'","''") +"%')";
        }
        if(foto.getId_CategoriaStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(fotos.id_categoria ="+foto.getId_CategoriaStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
    public Vector buscartitulo(fotosBEAN foto)throws Excecao{
       try{ 
         String SQL=SELECT+" where(fotos.titulo like'%"+foto.getTitulo().replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
    public Vector buscarPorCategoria(fotosBEAN foto)throws Excecao{
        try{
            String SQL="";
            if (
                    (foto.getId_CategoriaStr()==null)
                    ||
                    (
                        (foto.getId_CategoriaStr()!=null)
                         &&
                        (foto.getId_Categoria()==0)
                    )
            )
            {
                String id=(new categoriaFotosDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                foto.setId_Categoria(id);
            }
            else{}
            if (foto.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(fotos.id_categoria="+foto.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by fotos.id desc ";
            }
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorCategoria(fotosBEAN foto,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (foto.getId_CategoriaStr()==null)
                    ||
                    (
                        (foto.getId_CategoriaStr()!=null)
                         &&
                        (foto.getId_Categoria()==0)
                    )
            )
            {
                String id=(new categoriaFotosDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                foto.setId_Categoria(id);
            }
            if (foto.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(fotos.id_categoria="+foto.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by fotos.id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaFotosBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            fotosBEAN foto=new fotosBEAN();
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
                String id=(new categoriaFotosDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                
                foto.setId_Categoria(id);
            }
            else{
                foto.setId_Categoria(categoria.getIDStr());
            }
            if (foto.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(fotos.id_categoria="+foto.getId_CategoriaStr()+") ";
            }
            else{
                SQL=SELECT+" order by fotos.Id desc ";
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaFotosBEAN categoria)throws Excecao{
        try{
            String SQL="";
            fotosBEAN foto=new fotosBEAN();
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
                String id=(new categoriaFotosDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                foto.setId_Categoria(id);
            }            
            else{
                foto.setId_Categoria(categoria.getIDStr());
            }
            if (foto.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(fotos.id_categoria="+foto.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT+" order by fotos.Id desc ;";
            }
         
            return buscar(SQL);
        }
        catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
        }
    }
    public fotosBEAN buscarUltimo(){
       fotosBEAN acc = new fotosBEAN();
        try{ 
         String SQL=" select distinct id,titulo,descricao,src, id_categoria from fotos order by id desc ";
         
         return ((fotosBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
    public Vector buscarUltimos(int quantidade)throws Excecao{
       try{ 
         String SQL=" select distinct id,titulo,descricao,src, id_categoria from fotos order by id desc limit 0 ,0"+quantidade;
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
}
