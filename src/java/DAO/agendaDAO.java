package DAO;
import BD.Conexao;
import BEAN.agendaBEAN;
import POJO.agendaPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Vector;
public class agendaDAO {
    private Conexao MinhaConexao=null;
    public agendaDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private agendaBEAN inserir(agendaBEAN agenda)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into agenda(titulo,evento,descricao,src,dataevento) "
                    + "values("
                    + ""+Conexao.sqlProtection(agenda.getTitulo())+""
                    + ","
                    + ""+Conexao.sqlProtection(agenda.getEvento())+ ""
                    + ","
                    + ""+Conexao.sqlProtection(agenda.getdescricao())+ ""
                    + ","                    
                    + ""+Conexao.sqlProtection(agenda.getSrc())+ ""
                    + ","                    
                    + ""+Conexao.sqlProtection(agenda.getDataEventoStr("yyyy-MM-dd"))+ ""                    
                    + ")");
            this.MinhaConexao.Executa("commit");
            return buscarUltimo();
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar agenda.");}    
    }
    private agendaBEAN alterar(agendaBEAN agenda,String path_upload)throws Exception{
        try{
            agendaBEAN acc_agenda=buscarID(agenda);
            String SQL="update agenda set ";
                   SQL+= "titulo=" +Conexao.sqlProtection(agenda.getTitulo()) +"";
                   SQL+= ",";
                   SQL+= "evento="+Conexao.sqlProtection(agenda.getEvento()) +"";
                   SQL+= ",";
                   SQL+= "descricao="+Conexao.sqlProtection(agenda.getdescricao()) +"";
                   if(acc_agenda.getIDStr()!=null){
                        if (agenda.getSrc()!=null){
                            functions.deletaImagensRedimencionadas(path_upload,acc_agenda.getSrc());
                            SQL+= ",";
                            SQL+= "src="+Conexao.sqlProtection(agenda.getSrc()) +"";    
                        }
                   }
                   SQL+= ",";                    
                   SQL+= "dataevento="+Conexao.sqlProtection(agenda.getDataEventoStr("yyyy-MM-dd")) +"";
                   SQL+= " where(id="+agenda.getIDStr()+")";
                             
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            agenda=buscarID(agenda);
            return agenda;
        }
        catch(Exception erro){throw new Exception("Erro ao alterar Titulo da agenda.");}          
    }
    public agendaBEAN excluir(agendaBEAN agenda,String path_upload)throws Exception{
        try{
            agenda=buscarID(agenda);
            if(agenda.getIDStr()!=null){
                if (agenda.getSrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,agenda.getSrc());
                }
            }
            this.MinhaConexao.Executa("delete from agenda where(id="+agenda.getIDStr()+")");
            this.MinhaConexao.Executa("commit");
            return agenda;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir agenda.");}
    }
    public agendaBEAN salvar(agendaBEAN agenda,String path_upload)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((agenda.getIDStr()!=null)&&(buscarID(agenda).getIDStr()!=null)){
            return alterar(agenda,path_upload);
        }
        else{
           return inserir(agenda);
        }
    }
    public agendaBEAN buscarUltimo()throws Excecao{
       agendaBEAN ultimo = new agendaBEAN();
       try{ultimo= ((agendaBEAN)buscar("select id,titulo,evento,dataevento,src from agenda order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public agendaBEAN buscarID(agendaBEAN agenda)throws Excecao{
       agendaBEAN registro = new agendaBEAN();
       try{registro= ((agendaBEAN)buscar("select id,titulo,evento,dataevento,src from agenda where(id="+agenda.getIDStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo,evento,dataevento,src from agenda ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

     }
    public Vector buscarUltimos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo,evento,dataevento,src from agenda order by id desc");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

    }
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo,evento,dataevento,src from agenda where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
   public Vector buscar(String SQL)throws Excecao{
        Vector agendas=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,titulo,evento,descricao,dataevento,src from agenda";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       agendaPOJO POJO = new  agendaPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                       POJO.setEvento(MinhaConexao.MostrarCampoStr("evento"));
                       POJO.setdescricao(MinhaConexao.MostrarCampoStr("descricao"));
                       POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                       POJO.setDataEvento(MinhaConexao.MostrarCampoDate("dataEvento"));
                       agendaBEAN agenda_BEAN= new agendaBEAN(POJO);
                       agendas.add(agenda_BEAN);
                  }                                                  
            }
            return agendas;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar agenda! ERRO: ");
        }
    }
    public Vector buscarTodosPares()throws Excecao{
        Vector agendas=new Vector();
        try{
            String SQL = "select id,titulo,evento,descricao,dataevento,src from agenda order by id desc";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2==0){
                           agendaPOJO POJO = new  agendaPOJO();
                           POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                           POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                           POJO.setEvento(MinhaConexao.MostrarCampoStr("evento"));
                           POJO.setdescricao(MinhaConexao.MostrarCampoStr("descricao"));
                           POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                           POJO.setDataEvento(MinhaConexao.MostrarCampoDate("dataEvento"));
                           agendaBEAN agenda_BEAN= new agendaBEAN(POJO);
                           agendas.add(agenda_BEAN);
                       }
                       i++;
                  }                                                  
            }
            return agendas;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar agenda! ERRO: ");
        }
    }
    public Vector buscarTodosImpares()throws Excecao{
        Vector agendas=new Vector();
        try{
            String SQL = "select id,titulo,evento,descricao,dataevento,src from agenda order by id desc";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2==1){
                           agendaPOJO POJO = new  agendaPOJO();
                           POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                           POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                           POJO.setEvento(MinhaConexao.MostrarCampoStr("evento"));
                           POJO.setdescricao(MinhaConexao.MostrarCampoStr("descricao"));
                           POJO.setSrc(MinhaConexao.MostrarCampoStr("src"));
                           POJO.setDataEvento(MinhaConexao.MostrarCampoDate("dataEvento"));
                           agendaBEAN agenda_BEAN= new agendaBEAN(POJO);
                           agendas.add(agenda_BEAN);
                       }
                       i++;
                  }                                                  
            }
            return agendas;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar agenda! ERRO: ");
        }
    }
    public Vector buscar(agendaBEAN agenda)throws Excecao{
        Vector agendas=new Vector();
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo,evento,descricao,dataevento,src from agenda ";
            String condicao="";
            if (agenda.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(titulo like'%"+agenda.getTitulo().replaceAll("'","''")+"%')";
            }    
            if (agenda.getEvento()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(evento like'%"+agenda.getEvento().replaceAll("'","''")+"%')";
            }   
            if (agenda.getdescricao()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(evento like'%"+agenda.getdescricao().replaceAll("'","''")+"%')";
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
