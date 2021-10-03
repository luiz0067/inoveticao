package DAO;
import BD.Conexao;
import BEAN.contagemBEAN;
import POJO.contagemPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Date;
import java.util.Vector;
public class contagemDAO {
    private Conexao MinhaConexao=null;
    public contagemDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private contagemBEAN inserir(contagemBEAN contagem)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into contagem(ip,data_cadastro) "
                    + "values("
                    + ""+Conexao.sqlProtection(contagem.getIP())+""
                    + ","
                    + ""+Conexao.sqlProtection(contagem.getDataCadastroStr("yyyy-MM-dd"))+ ""                    
                    + ")");
            this.MinhaConexao.Executa("commit");
            return buscarUltimo();
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar contagem.");}    
    }
    private contagemBEAN alterar(contagemBEAN contagem)throws Exception{
        try{
            contagem=buscarID(contagem);
            this.MinhaConexao.Executa("update contagem set "
                    + "ip=" +Conexao.sqlProtection(contagem.getIP()) +""
                    + ","
                    + "data_cadastro="+Conexao.sqlProtection(contagem.getDataCadastroStr("yyyy-MM-dd")) +""
                    + " where(id="+contagem.getIDStr()+")");
            this.MinhaConexao.Executa("commit");
            contagem=buscarID(contagem);
            return contagem;
        }
        catch(Exception erro){throw new Exception("Erro ao alterar ip da contagem.");}          
    }
    public contagemBEAN excluir(contagemBEAN contagem,String path_upload)throws Exception{
        try{
            contagem=buscarID(contagem);
            this.MinhaConexao.Executa("delete from contagem where(id="+contagem.getIDStr()+")");
            this.MinhaConexao.Executa("commit");
            return contagem;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir contagem.");}
    }
    public contagemBEAN salvar(contagemBEAN contagem)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((contagem.getIDStr()!=null)&&(buscarID(contagem).getIDStr()!=null)){
            return alterar(contagem);
        }
        else{
           return inserir(contagem);
        }
    }
    public contagemBEAN buscarUltimo()throws Excecao{
       contagemBEAN ultimo = new contagemBEAN();
       try{ultimo= ((contagemBEAN)buscar("select id,ip,data_cadastro from contagem order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public contagemBEAN buscarID(contagemBEAN contagem)throws Excecao{
       contagemBEAN registro = new contagemBEAN();
       try{registro= ((contagemBEAN)buscar("select id,ip,data_cadastro from contagem where(id="+contagem.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,ip,data_cadastro from contagem ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

     }
    public int contarTodos()throws Excecao{ 
       int contador=0;
       try{
           String SQL="select count(*) as contador from contagem ";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.MostrarCampoInteger("contador").intValue();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public int contarHoje()throws Excecao{ 
       int contador=0;
       try{
           Date hoje = new Date();
           String StrHoje = Until.functions.DateToString(hoje,"yyyy-MM-dd");
           String SQL="select count(*) as contador from contagem where (data_cadastro='"+StrHoje+"')";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.MostrarCampoInteger("contador").intValue();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public int contarOntem()throws Excecao{ 
       int contador=0;
       try{
           Date Ontem = Until.functions.Ontem();
           String StrOntem = Until.functions.DateToString(Ontem,"yyyy-MM-dd");
           String SQL="select count(*) as contador from contagem where (data_cadastro='"+StrOntem+"')";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.MostrarCampoInteger("contador").intValue();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public int contarEsteMes()throws Excecao{ 
       int contador=0;
       try{
           Date hoje = new Date();
           Date primeiro=Until.functions.PrimeiroDiaDoMes(hoje);
           Date ulimo=Until.functions.UltimoDiaDoMes(hoje);
           String StrPrimeiro = Until.functions.DateToString(primeiro,"yyyy-MM-dd");
           String StrUlimo = Until.functions.DateToString(ulimo,"yyyy-MM-dd");
           String SQL="select count(*) as contador from contagem where (data_cadastro between '"+StrPrimeiro+"' and '"+StrUlimo+"')";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.MostrarCampoInteger("contador").intValue();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public int contarTodosIPS()throws Excecao{ 
       int contador=0;
       try{
           String SQL="select distinct IP  from contagem ";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.Contar();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public int contarHojeIPS()throws Excecao{ 
       int contador=0;
       try{
           Date hoje = new Date();
           String StrHoje = Until.functions.DateToString(hoje,"yyyy-MM-dd");
           String SQL="select distinct IP  from contagem where (data_cadastro='"+StrHoje+"')";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.Contar();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public int contarEstaSemana()throws Excecao{ 
       int contador=0;
       try{
           Date hoje = new Date();
           Date primeiro=Until.functions.PrimeiroDiaDaSemana(hoje);
           Date ulimo=Until.functions.UltimoDiaDaSemana(hoje);
           String StrPrimeiro = Until.functions.DateToString(primeiro,"yyyy-MM-dd");
           String StrUlimo = Until.functions.DateToString(ulimo,"yyyy-MM-dd");
           String SQL="select count(*) as contador from contagem where (data_cadastro between '"+StrPrimeiro+"' and '"+StrUlimo+"')";
           if(MinhaConexao.Busca(SQL)){
                  if(MinhaConexao.MoverProximo())
                  {
                       contador=MinhaConexao.MostrarCampoInteger("contador").intValue();                             
                  }                                                  
            }
       }catch(Exception erro){
           throw new Excecao("Erro ao contar todos! ERRO:");
       }
       return contador;
    }
    public Vector buscarUltimos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,ip,data_cadastro from contagem order by id desc");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

    }
    public Vector buscarip(String ip)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,ip,data_cadastro from contagem where(ip like'%"+ip.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar ip ERRO:");}
       return registros;
    }
   public Vector buscar(String SQL)throws Excecao{
        Vector contagems=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,ip,data_cadastro from contagem";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       contagemPOJO POJO = new  contagemPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setIP(MinhaConexao.MostrarCampoStr("ip"));
                       POJO.setDataCadastro(MinhaConexao.MostrarCampoDate("data_cadastro"));
                       contagemBEAN contagem_BEAN= new contagemBEAN(POJO);
                       contagems.add(contagem_BEAN);
                  }                                                  
            }
            return contagems;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar contagem! ERRO: ");
        }
    }
    public Vector buscarTodosPares()throws Excecao{
        Vector contagems=new Vector();
        try{
            String SQL = "select id,ip,data_cadastro from contagem";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2==0){
                           contagemPOJO POJO = new  contagemPOJO();
                           POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                           POJO.setIP(MinhaConexao.MostrarCampoStr("ip"));
                           POJO.setDataCadastro(MinhaConexao.MostrarCampoDate("data_cadastro"));
                           contagemBEAN contagem_BEAN= new contagemBEAN(POJO);
                           contagems.add(contagem_BEAN);
                           i++;
                       }
                  }                                                  
            }
            return contagems;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar contagem! ERRO: ");
        }
    }
    public Vector buscarTodosImpares()throws Excecao{
        Vector contagems=new Vector();
        try{
            String SQL = "select id,ip,data_cadastro from contagem";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2!=0){
                           contagemPOJO POJO = new  contagemPOJO();
                           POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                           POJO.setIP(MinhaConexao.MostrarCampoStr("ip"));
                           POJO.setDataCadastro(MinhaConexao.MostrarCampoDate("data_cadastro"));
                           contagemBEAN contagem_BEAN= new contagemBEAN(POJO);
                           contagems.add(contagem_BEAN);
                           i++;
                       }
                  }                                                  
            }
            return contagems;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar contagem! ERRO: ");
        }
    }
    public Vector buscar(contagemBEAN contagem)throws Excecao{
        Vector contagems=new Vector();
        Vector registros=new Vector();
        try{
            String SQL="select id,ip,data_cadastro from contagem ";
            String condicao="";
            if (contagem.getIP()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(ip like'%"+contagem.getIP().replaceAll("'","''")+"%')";
            }    
            SQL=(condicao.length()>0)?SQL+"where"+condicao:SQL;        
            registros = buscar(SQL);
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    }
}
