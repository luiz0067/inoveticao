package DAO;

import BD.Conexao;
import BEAN.categoriaVideosBEAN;
import BEAN.videosBEAN;
import POJO.videosPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

public class videosDAO {
    private static final String SELECT="SELECT videos.id,videos.id_categoria,videos.titulo,videos.descricao,videos.src,categoriavideos.titulo as titulo_categoria from videos left join categoriavideos on(categoriavideos.id=videos.id_categoria)";
    private Conexao MinhaConexao=null;
    public videosDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public videosBEAN salvar(videosBEAN Videos)throws Exception{        
        videosBEAN resultado = buscarID(Videos);       
        if((Videos.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            resultado=alterar(Videos);
        }
        else{
           resultado=inserir(Videos); 
        }
        return resultado;
    }
    private videosBEAN inserir(videosBEAN Videos) throws Exception{
        String SQL="insert into videos(titulo,descricao,src,id_categoria) values("                   
            +Conexao.sqlProtection(Videos.getTitulo())+","
            +Conexao.sqlProtection(Videos.getDescricao())+","
            +Conexao.sqlProtection(Videos.getSrc())+","
            +Videos.getId_CategoriaStr()+""
            + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Video.");
        }   
        return buscarUltimo();
    }
    private videosBEAN alterar(videosBEAN Videos) throws Exception{
        String SQL=" update videos set "
        +"titulo=" +Conexao.sqlProtection(Videos.getTitulo())+"," 
        +"descricao="+Conexao.sqlProtection(Videos.getDescricao())+","
        +"src="+Conexao.sqlProtection(Videos.getSrc())+","
        +"id_categoria="+Videos.getId_CategoriaStr()+" "
        +" where(id="+Videos.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Video.");
        }   
        return buscarID(Videos);
    }
    public videosBEAN excluir(videosBEAN Videos)throws Exception{
        String SQL="delete from videos where(id="+Videos.getID()+")";
        try{
            Videos = buscarID(Videos);
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return Videos;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir Video.");
        }
    }
    public videosBEAN buscarID(videosBEAN Video)throws Excecao{
        videosBEAN acc = new videosBEAN();
        try{
            String SQL = SELECT+" where(videos.id=0"+Video.getIDStr()+");";                   
            acc=(videosBEAN)buscar(SQL).get(0); 
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
                    videosPOJO POJO = new  videosPOJO();
                    POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                    POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                    POJO.setDescricao(MinhaConexao.MostrarCampoStr("descricao"));
                    POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                    POJO.setId_Categoria(MinhaConexao.MostrarCampoInteger("id_categoria"));
                    POJO.setTitulo_Categoria(MinhaConexao.MostrarCampoStr("titulo_categoria"));
                    videosBEAN Videos_BEAN = new videosBEAN(POJO);
                    registros.add(Videos_BEAN);
                }          
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar Video! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Video! ERRO:");
       }
     }
     public Vector buscarUltimos(int quantidade)throws Excecao{
       try{ 
         return buscar(SELECT+" order by videos.id desc limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Video! ERRO:");
       }
     }
     public Vector buscarUltimos()throws Excecao{
       try{ 
         return buscar(SELECT+"  order by videos.id desc ");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Video! ERRO:");
       }
     }
    public Vector buscartitulo(String titulo)throws Excecao{
       try{ 
         String SQL=SELECT+"  where(videos.titulo like'%"+titulo.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Video! ERRO:");
       }
     }
    public Vector buscar(videosBEAN videos)throws Excecao{
        try{
        String SQL=SELECT;
        String condicao="";
        if (videos.getTitulo()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(videos.titulo like'%"+videos.getTitulo().replaceAll("'","''") +"%')";
        }
        if(videos.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(videos.descricao like'%"+videos.getDescricao().replaceAll("'","''") +"%')";
        }
        if(videos.getSrc()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(videos.src like'%"+videos.getSrc().replaceAll("'","''") +"%')";
        }
        if(videos.getId_CategoriaStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(videos.id_categoria ="+videos.getId_CategoriaStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar video! ERRO:");
       }
     }
    public Vector buscarPorCategoria(videosBEAN video)throws Excecao{
        try{
            String SQL="";
            if (video.getId_CategoriaStr()==null){
                video.setId_Categoria((new categoriaVideosDAO(MinhaConexao)).buscarPrimeiro().getIDStr());
            }
            if (video.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(videos.id_categoria="+video.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT;
            }
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorCategoria(videosBEAN video,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (video.getId_CategoriaStr()==null){
                video.setId_Categoria((new categoriaVideosDAO(MinhaConexao)).buscarPrimeiro().getIDStr());
            }
            if (video.getId_CategoriaStr()!=null){
                SQL=SELECT+" where(videos.id_categoria="+video.getId_CategoriaStr()+");";
            }
            else{
                SQL=SELECT;
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public Vector buscarPorCategoria(categoriaVideosBEAN categoria)throws Excecao{
        try{
            String SQL="";
            if (categoria.getIDStr()==null){
                categoria.setID((new categoriaVideosDAO(MinhaConexao)).buscarPrimeiro().getIDStr());
            }
            if (categoria.getIDStr()!=null){
                SQL=SELECT+" where(videos.id_categoria="+categoria.getIDStr()+");";
            }
            else{
                SQL=SELECT;
            }         
            return buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar foto! ERRO:");
        }
    }
    public Vector buscarPorCategoria(categoriaVideosBEAN categoria,int quantidade)throws Excecao{
        try{
            String SQL="";
            if (categoria.getIDStr()==null){
                categoria.setID((new categoriaVideosDAO(MinhaConexao)).buscarPrimeiro().getIDStr());
            }
            if (categoria.getIDStr()!=null){
                SQL=SELECT+" where(videos.id_categoria="+categoria.getIDStr()+");";
            }
            else{
                SQL=SELECT;
            }
         
         return buscar(SQL+" limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar foto! ERRO:");
       }
    }
    public videosBEAN buscarUltimo(){
       videosBEAN acc = new videosBEAN();
        try{ 
         String SQL=SELECT+"  order by videos.id desc";
         
         return ((videosBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
}
