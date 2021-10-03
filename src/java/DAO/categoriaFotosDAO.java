package DAO;
import BD.Conexao;
import BEAN.categoriaFotosBEAN;
import POJO.categoriaFotosPOJO;
import excecoes.Excecao;
import java.util.Vector;
public class categoriaFotosDAO {
    private Conexao MinhaConexao=null;
    public categoriaFotosDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(categoriaFotosBEAN categoriafotos)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into categoriafotos(titulo) values("+Conexao.sqlProtection(categoriafotos.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar categoriafotos.");}    
    }
    private void alterar(categoriaFotosBEAN categoriafotos)throws Exception{
        try{
            this.MinhaConexao.Executa("update categoriafotos set titulo=" +Conexao.sqlProtection(categoriafotos.getTitulo()) +" where(id="+categoriafotos.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da categoriafotos.");}          
    }
    public categoriaFotosBEAN excluir(categoriaFotosBEAN categoriafotos)throws Exception{
        try{
            categoriafotos=buscarID(categoriafotos);
            this.MinhaConexao.Executa("delete from categoriafotos where(id="+categoriafotos.getID()+")");
            this.MinhaConexao.Executa("commit");
            return categoriafotos;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir categoriafotos.");}
    }
    public categoriaFotosBEAN salvar(categoriaFotosBEAN categoriafotos)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((categoriafotos.getIDStr()!=null)&&(buscarID(categoriafotos).getIDStr()!=null)){
            alterar(categoriafotos);
            return buscarID(categoriafotos);
        }
        else{
           inserir(categoriafotos);
           return buscarUltimo();
        }
    } 
    public categoriaFotosBEAN buscarUltimo()throws Excecao{
       categoriaFotosBEAN ultimo = new categoriaFotosBEAN();
       try{ultimo= ((categoriaFotosBEAN)buscar("select id,titulo from categoriafotos order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public categoriaFotosBEAN buscarPrimeiro()throws Excecao{
       categoriaFotosBEAN Primeiro = new categoriaFotosBEAN();
       try{Primeiro= ((categoriaFotosBEAN)buscar("select id,titulo from categoriafotos order by Titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public categoriaFotosBEAN buscarID(categoriaFotosBEAN categoriafotos)throws Excecao{
       categoriaFotosBEAN registro = new categoriaFotosBEAN();
       try{registro= ((categoriaFotosBEAN)buscar("select id,titulo from categoriafotos where(ID="+categoriafotos.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriafotos ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriafotos where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(categoriaFotosBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo from categoriafotos ";
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
       //try{registros = buscar(" select distinct categoriafotos.id,categoriafotos.titulo from categoriafotos right join fotos on(fotos.id_categoria=categoriafotos.id) order by fotos.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select categoriafotos.id,categoriafotos.titulo from categoriafotos order by categoriafotos.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector categoriafotoss=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from categoriafotos";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       categoriaFotosPOJO POJO = new  categoriaFotosPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       categoriaFotosBEAN categoriafotos_BEAN= new categoriaFotosBEAN(POJO);
                       categoriafotoss.add(categoriafotos_BEAN);
                  }                                                  
            }
            return categoriafotoss;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar categoriafotos! ERRO: ");
        }
    }
}