package DAO;
import BD.Conexao;
import BEAN.categoriaBannersBEAN;
import POJO.categoriaBannersPOJO;
import excecoes.Excecao;
import java.util.Vector;

public class categoriaBannersDAO {
    private Conexao MinhaConexao=null;
    public categoriaBannersDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(categoriaBannersBEAN categoriaBanners)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into categoriabanners(titulo) values("+Conexao.sqlProtection(categoriaBanners.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar categoriaBanners.");}    
    }
    private void alterar(categoriaBannersBEAN categoriaBanners)throws Exception{
        try{
            this.MinhaConexao.Executa("update categoriabanners set titulo=" +Conexao.sqlProtection(categoriaBanners.getTitulo()) +" where(id="+categoriaBanners.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da categoriaBanners.");}          
    }
    public categoriaBannersBEAN excluir(categoriaBannersBEAN categoriaBanners)throws Exception{
        try{
            categoriaBanners=buscarID(categoriaBanners);
            this.MinhaConexao.Executa("delete from categoriabanners where(id="+categoriaBanners.getID()+")");
            this.MinhaConexao.Executa("commit");
            return categoriaBanners;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir categoriaBanners.");}
    }
    public categoriaBannersBEAN salvar(categoriaBannersBEAN categoriaBanners)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((categoriaBanners.getIDStr()!=null)&&(buscarID(categoriaBanners).getIDStr()!=null)){
            alterar(categoriaBanners);
            return buscarID(categoriaBanners);
        }
        else{
           inserir(categoriaBanners);
           return buscarUltimo();
        }
    } 
    public categoriaBannersBEAN buscarUltimo()throws Excecao{
       categoriaBannersBEAN ultimo = new categoriaBannersBEAN();
       try{ultimo= ((categoriaBannersBEAN)buscar("select id,titulo from categoriabanners order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public categoriaBannersBEAN buscarPrimeiro()throws Excecao{
       categoriaBannersBEAN Primeiro = new categoriaBannersBEAN();
       try{Primeiro= ((categoriaBannersBEAN)buscar("select id,titulo from categoriabanners order by Titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public categoriaBannersBEAN buscarID(categoriaBannersBEAN categoriaBanners)throws Excecao{
       categoriaBannersBEAN registro = new categoriaBannersBEAN();
       try{registro= ((categoriaBannersBEAN)buscar("select id,titulo from categoriabanners where(ID="+categoriaBanners.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriabanners ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriabanners where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(categoriaBannersBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo from categoriabanners ";
            String condicao="";
            if (categoria.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(titulo like '%"+categoria.getTitulo().replaceAll("'","''") +"%')";
            }       
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;        
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
    public Vector buscarUltimasAtualizadas()throws Excecao{
       Vector registros=new Vector();
       //try{registros = buscar(" select distinct categoriaBanners.id,categoriaBanners.titulo from categoriabanners right join banners on(banners.id_categoria=categoriaBanners.id) order by banners.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select categoriaBanners.id,categoriaBanners.titulo from categoriabanners order by categoriaBanners.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from categoriabanners";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       categoriaBannersPOJO POJO = new  categoriaBannersPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       categoriaBannersBEAN categoriabanners_BEAN= new categoriaBannersBEAN(POJO);
                       registros.add(categoriabanners_BEAN);
                  }                                                  
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar categoriaBanners! ERRO: ");
        }
    }
}