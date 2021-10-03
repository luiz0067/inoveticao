package DAO;

import BD.Conexao;
import BEAN.contatoBEAN;
import POJO.contatoPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

public class contatoDAO {
    private static final String SELECT="SELECT contato.id,contato.ativo,contato.nome,contato.email,contato.data_cadastro from contato";
    private Conexao MinhaConexao=null;
    public contatoDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public contatoBEAN Ativar(contatoBEAN contato) throws Exception{
        String SQL=" update contato set "
        +"ativo=true "
        +" where(id="+contato.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarID(contato);
    }
    public contatoBEAN Desativar(contatoBEAN contato) throws Exception{
        String SQL=" update contato set "
        +"ativo=false"
        +" where(id="+contato.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarID(contato);
    }    
    public contatoBEAN salvar(contatoBEAN contato)throws Exception{        
        contatoBEAN resultado = buscarID(contato);       
        if((contato.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            resultado=alterar(contato);
        }
        else{
           resultado=inserir(contato); 
        }
        return resultado;
    }
    private contatoBEAN inserir(contatoBEAN contato) throws Exception{
        String SQL="insert into contato(nome,email,data_cadastro,ativo) values("                   
            +Conexao.sqlProtection(contato.getNome())+","
            +Conexao.sqlProtection(contato.getEmail())+","
            +Conexao.sqlProtection(contato.getData_CadastroStr("yyyy-MM-dd HH-mm-ss"))+","
            +contato.getAtivoStr()+""
            + ")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarUltimo();
    }
    private contatoBEAN alterar(contatoBEAN contato) throws Exception{
        String SQL=" update contato set "
        +"nome=" +Conexao.sqlProtection(contato.getNome())+"," 
        +"email="+Conexao.sqlProtection(contato.getEmail())+","
        +"data_cadastro="+Conexao.sqlProtection(contato.getData_CadastroStr("yyyy-MM-dd"))+","
        +"ativo="+contato.getAtivoStr()+" "
        +" where(id="+contato.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarID(contato);
    }
    public contatoBEAN excluir(contatoBEAN contato)throws Exception{
        String SQL="delete from contato where(id="+contato.getID()+")";
        try{
            contato = buscarID(contato);
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return contato;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir Comentario.");
        }
    }
    public contatoBEAN buscarID(contatoBEAN Comentario)throws Excecao{
        contatoBEAN acc = new contatoBEAN();
        try{
            String SQL = SELECT+" where(contato.id=0"+Comentario.getIDStr()+");";                   
            acc=(contatoBEAN)buscar(SQL).get(0); 
        }catch (Exception erro){}
        return acc;

    }   
    public contatoBEAN buscarEmail(contatoBEAN Comentario)throws Excecao{
        contatoBEAN acc = new contatoBEAN();
        try{
            String SQL = SELECT+" where(contato.email="+Conexao.sqlProtection(Comentario.getEmail())+");";                   
            acc=(contatoBEAN)buscar(SQL).get(0); 
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
                    contatoPOJO POJO = new  contatoPOJO();
                    POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                    POJO.setNome(MinhaConexao.MostrarCampoStr("nome"));
                    POJO.setEmail(MinhaConexao.MostrarCampoStr("email"));
                    POJO.setData_Cadastro(MinhaConexao.MostrarCampoDate("data_cadastro"));
                    POJO.setAtivo(MinhaConexao.MostrarCampoBoolean("ativo"));
                    contatoBEAN contato_BEAN = new contatoBEAN(POJO);
                    registros.add(contato_BEAN);
                }          
            }
            return registros;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar Comentario! ERRO: ");
        }
    }
    public Vector buscarTodos()throws Excecao{
       try{ 
         return buscar(SELECT);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    public Vector buscarAtivos()throws Excecao{
       try{ 
         return buscar(SELECT+" where (ativo=true)");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
     public Vector buscarUltimos(int quantidade)throws Excecao{
       try{ 
         return buscar(SELECT+" order by contato.id desc limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
     public Vector buscarUltimos()throws Excecao{
       try{ 
         return buscar(SELECT+"  order by contato.id desc ");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    public Vector buscarnome(String nome)throws Excecao{
       try{ 
         String SQL=SELECT+"  where(contato.nome like'%"+nome.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    public Vector buscar(contatoBEAN contato)throws Excecao{
        try{
        String SQL=SELECT;
        String condicao="";
        if (contato.getNome()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(contato.nome like'%"+contato.getNome().replaceAll("'","''") +"%')";
        }
        if(contato.getEmail()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(contato.email like'%"+contato.getEmail().replaceAll("'","''") +"%')";
        }
        if(contato.getData_Cadastro()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(contato.data_cadastro ="+Conexao.sqlProtection(contato.getData_CadastroStr("yyyy-MM-dd"))+")";
        }
        if(contato.getAtivoStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(contato.ativo ="+contato.getAtivoStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    
    
    
    public contatoBEAN buscarUltimo(){
       contatoBEAN acc = new contatoBEAN();
        try{ 
         String SQL=SELECT+"  order by contato.id desc";
         
         return ((contatoBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
}
