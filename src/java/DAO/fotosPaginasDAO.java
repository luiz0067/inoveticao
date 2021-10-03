package DAO;

import BD.Conexao;
import BEAN.paginasBEAN;
import BEAN.fotosPaginasBEAN;
import POJO.fotosPaginasPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class fotosPaginasDAO {
    private Conexao MinhaConexao=null;
    private static final String SELECT="select distinct fotospaginas.id,fotospaginas.titulo,fotospaginas.descricao,fotospaginas.src, fotospaginas.id_paginas, paginas.titulo as titulo_paginas from fotospaginas left join paginas on(fotospaginas.id_paginas=paginas.id)";
    public fotosPaginasDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public fotosPaginasBEAN salvar(fotosPaginasBEAN fotospaginas,String path_upload)throws Exception{        
        fotosPaginasBEAN resultado = buscarID(fotospaginas);       
        if((fotospaginas.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            resultado=alterar(fotospaginas,path_upload);
        }
        else{
           resultado=inserir(fotospaginas); 
        }
        return resultado;
    }
        
    
    private fotosPaginasBEAN inserir(fotosPaginasBEAN fotospaginas) throws Exception{
        String SQL=" insert into fotospaginas(titulo,descricao,src,id_paginas) values("                   
            +Conexao.sqlProtection(fotospaginas.getTitulo())+","
            +Conexao.sqlProtection(fotospaginas.getDescricao())+","
            +Conexao.sqlProtection(fotospaginas.getSrc())+","
            +fotospaginas.getId_PaginasStr()+""
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
    private fotosPaginasBEAN alterar(fotosPaginasBEAN fotospaginas,String path_upload) throws Exception{
        try{
        String SQL=" update fotospaginas set ";
        SQL+="titulo=" +Conexao.sqlProtection(fotospaginas.getTitulo())+"," ;
        SQL+="descricao="+Conexao.sqlProtection(fotospaginas.getDescricao())+" ";
        if((fotospaginas.getSrc()!=null)&&(fotospaginas.getSrc().length()!=0)){
            SQL+=",";
            SQL+="src="+Conexao.sqlProtection(fotospaginas.getSrc())+" ";
            fotosPaginasBEAN fotos1=buscarID(fotospaginas);
            if((fotos1.getIDStr()!=null)&&((fotos1.getSrc()!=null)&&(fotos1.getSrc().length()!=0))){
                if (fotos1.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,fotos1.getSrc());
                }
            }  
        }
        SQL+=",";
        SQL+=" id_paginas="+fotospaginas.getId_PaginasStr()+" ";
        SQL+=" where(id="+fotospaginas.getIDStr()+")";            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar foto.");
        }   
        return buscarID(fotospaginas);
    }
    public fotosPaginasBEAN excluir(fotosPaginasBEAN fotospaginas,String path_upload)throws Exception{
        String SQL="delete from fotospaginas where(id="+fotospaginas.getIDStr()+")";
        try{
            fotospaginas=buscarID(fotospaginas);
            if(fotospaginas.getIDStr()!=null){
                if (fotospaginas.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,fotospaginas.getSrc());
                }
            }            
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return fotospaginas;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir foto.");
        }
    }
    public fotosPaginasBEAN buscarID(fotosPaginasBEAN foto)throws Excecao{
        fotosPaginasBEAN acc = new fotosPaginasBEAN();
        try{
            String SQL = SELECT+" where(fotospaginas.id="+foto.getIDStr()+");";                   
            acc=(fotosPaginasBEAN)buscar(SQL).get(0); 
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
                       fotosPaginasPOJO POJO = new  fotosPaginasPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                       POJO.setId_Paginas(MinhaConexao.MostrarCampoInteger("id_paginas"));
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                       POJO.setTitulo_Paginas(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                       POJO.setDescricao(MinhaConexao.MostrarCampoStr("Descricao"));
                       POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                       fotosPaginasBEAN fotos_BEAN = new fotosPaginasBEAN(POJO);
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
    
    public Vector buscar(fotosPaginasBEAN foto)throws Excecao{
       try{
        String SQL=SELECT;
        String condicao="";
        if (foto.getTitulo()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(fotospaginas.titulo like'%"+foto.getTitulo().replaceAll("'","''") +"%')";
        }
        if(foto.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(fotospaginas.descricao like'%"+foto.getDescricao().replaceAll("'","''") +"%')";
        }
        if(foto.getId_PaginasStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(fotospaginas.id_paginas ="+foto.getId_PaginasStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
    public Vector buscartitulo(fotosPaginasBEAN foto)throws Excecao{
       try{ 
         String SQL=SELECT+" where(fotospaginas.titulo like '%"+foto.getTitulo().replaceAll("'","''")+"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
     }
    public Vector buscarPorPagina(fotosPaginasBEAN foto)throws Excecao{
        try{
            String SQL="";
            if (
                    (foto.getId_PaginasStr()==null)
                    ||
                    (
                        (foto.getId_PaginasStr()!=null)
                         &&
                        (foto.getId_Paginas()==0)
                    )
            )
            {
                String id=(new categoriaFotosDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                foto.setId_Paginas(id);
            }
            SQL=SELECT+" where(fotospaginas.id_paginas="+foto.getId_PaginasStr()+");";
            return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorPagina(fotosPaginasBEAN foto,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (
                    (foto.getId_PaginasStr()==null)
                    ||
                    (
                        (foto.getId_PaginasStr()!=null)
                         &&
                        (foto.getId_Paginas()==0)
                    )
            )
            {
                String id=(new categoriaFotosDAO(MinhaConexao)).buscarPrimeiro().getIDStr();
                foto.setId_Paginas(id);
            }
            
                SQL=SELECT+" where(fotospaginas.id_paginas="+foto.getId_PaginasStr()+");";
           
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorPagina(paginasBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            fotosPaginasBEAN foto=new fotosPaginasBEAN();
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
                
                foto.setId_Paginas(id);
            }
            else{
                foto.setId_Paginas(categoria.getIDStr());
            }
            SQL=SELECT+" where(fotospaginas.id_paginas="+foto.getId_PaginasStr()+") ";
            return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorPagina(paginasBEAN categoria)throws Excecao{
        try{
            String SQL="";
            fotosPaginasBEAN foto=new fotosPaginasBEAN();
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
                foto.setId_Paginas(id);
            }            
            else{
                foto.setId_Paginas(categoria.getIDStr());
            }
            SQL=SELECT+" where(fotospaginas.id_paginas="+foto.getId_PaginasStr()+");";
            return buscar(SQL);
        }
        catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
        }
    }
    public fotosPaginasBEAN buscarUltimo(){
       fotosPaginasBEAN acc = new fotosPaginasBEAN();
        try{ 
         String SQL=" select distinct id,titulo,descricao,src, id_paginas from fotospaginas order by id desc ";
         
         return ((fotosPaginasBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
    public Vector buscarUltimos(int quantidade)throws Excecao{
        try{ 
            String SQL=" select distinct id,titulo,descricao,src, id_paginas from fotospaginas order by id desc limit 0 ,0"+quantidade;
            return buscar(SQL);
        }
        catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
        }
    }
}