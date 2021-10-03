package DAO;
import BD.Conexao;
import BEAN.categoriaPatrocinadoresBEAN;
import POJO.categoriaPatrocinadoresPOJO;
import excecoes.Excecao;
import java.util.Vector;

public class categoriaPatrocinadoresDAO {
    private Conexao MinhaConexao=null;
    public categoriaPatrocinadoresDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private void inserir(categoriaPatrocinadoresBEAN categoriaPatrocinadores)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into categoriapatrocinadores(titulo) values("+Conexao.sqlProtection(categoriaPatrocinadores.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar categoriaPatrocinadores.");}    
    }
    private void alterar(categoriaPatrocinadoresBEAN categoriaPatrocinadores)throws Exception{
        try{
            this.MinhaConexao.Executa("update categoriapatrocinadores set titulo=" +Conexao.sqlProtection(categoriaPatrocinadores.getTitulo()) +" where(id="+categoriaPatrocinadores.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar titulo da categoriaPatrocinadores.");}          
    }
    public categoriaPatrocinadoresBEAN excluir(categoriaPatrocinadoresBEAN categoriaPatrocinadores)throws Exception{
        try{
            categoriaPatrocinadores=buscarID(categoriaPatrocinadores);
            this.MinhaConexao.Executa("delete from categoriapatrocinadores where(id="+categoriaPatrocinadores.getID()+")");
            this.MinhaConexao.Executa("commit");
            return categoriaPatrocinadores;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir categoriaPatrocinadores.");}
    }
    public categoriaPatrocinadoresBEAN salvar(categoriaPatrocinadoresBEAN categoriaPatrocinadores)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((categoriaPatrocinadores.getIDStr()!=null)&&(buscarID(categoriaPatrocinadores).getIDStr()!=null)){
            alterar(categoriaPatrocinadores);
            return buscarID(categoriaPatrocinadores);
        }
        else{
           inserir(categoriaPatrocinadores);
           return buscarUltimo();
        }
    } 
    public categoriaPatrocinadoresBEAN buscarUltimo()throws Excecao{
       categoriaPatrocinadoresBEAN ultimo = new categoriaPatrocinadoresBEAN();
       try{ultimo= ((categoriaPatrocinadoresBEAN)buscar("select id,titulo from categoriapatrocinadores order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public categoriaPatrocinadoresBEAN buscarPrimeiro()throws Excecao{
       categoriaPatrocinadoresBEAN Primeiro = new categoriaPatrocinadoresBEAN();
       try{Primeiro= ((categoriaPatrocinadoresBEAN)buscar("select id,titulo from categoriapatrocinadores order by Titulo asc").get(0));}catch(Exception erro){}
       return Primeiro;
    }
    public categoriaPatrocinadoresBEAN buscarID(categoriaPatrocinadoresBEAN categoriaPatrocinadores)throws Excecao{
       categoriaPatrocinadoresBEAN registro = new categoriaPatrocinadoresBEAN();
       try{registro= ((categoriaPatrocinadoresBEAN)buscar("select id,titulo from categoriapatrocinadores where(ID="+categoriaPatrocinadores.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriapatrocinadores ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;
    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriapatrocinadores where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(categoriaPatrocinadoresBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo from categoriapatrocinadores ";
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
       //try{registros = buscar(" select distinct categoriaPatrocinadores.id,categoriaPatrocinadores.titulo from categoriapatrocinadores right join patrocinadores on(patrocinadores.id_categoria=categoriaPatrocinadores.id) order by patrocinadores.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       try{registros = buscar(" select categoriaPatrocinadores.id,categoriaPatrocinadores.titulo from categoriapatrocinadores order by categoriaPatrocinadores.id desc ");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(String SQL)throws Excecao{
        Vector registros=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo from categoriapatrocinadores";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       categoriaPatrocinadoresPOJO POJO = new  categoriaPatrocinadoresPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       categoriaPatrocinadoresBEAN categoriaPatrocinadores_BEAN= new categoriaPatrocinadoresBEAN(POJO);
                       registros.add(categoriaPatrocinadores_BEAN);
                  }                                                  
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar categoriaPatrocinadores! ERRO: ");
        }
    }
}