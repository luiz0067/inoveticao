package DAO;

import BD.Conexao;
import BEAN.comentariosBEAN;
import BEAN.menuBEAN;
import POJO.comentariosPOJO;
import Until.functions;
import excecoes.Excecao;
import java.io.File;
import java.util.Vector;

public class comentariosDAO {
    private static final String SELECT="SELECT comentarios.id,comentarios.exibir,comentarios.titulo,comentarios.descricao,comentarios.data_cadastro, comentarios.id_menu,menu.titulo from comentarios left join menu on(menu.id=comentarios.id_menu)";
    private Conexao MinhaConexao=null;
    public comentariosDAO (Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    public comentariosBEAN Ativar(comentariosBEAN comentarios) throws Exception{
        String SQL=" update comentarios set "
        +"exibir=true "
        +" where(id="+comentarios.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarID(comentarios);
    }
    public comentariosBEAN Desativar(comentariosBEAN comentarios) throws Exception{
        String SQL=" update comentarios set "
        +"exibir=false"
        +" where(id="+comentarios.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarID(comentarios);
    }    
    public comentariosBEAN salvar(comentariosBEAN comentarios)throws Exception{        
        comentariosBEAN resultado = buscarID(comentarios);       
        if((comentarios.getIDStr()!=null)&&(resultado.getIDStr()!=null)){
            resultado=alterar(comentarios);
        }
        else{
           resultado=inserir(comentarios); 
        }
        return resultado;
    }
    private comentariosBEAN inserir(comentariosBEAN comentarios) throws Exception{
        String SQL="insert into comentarios(titulo,descricao,data_cadastro,exibir,id_menu) values("                   
            +Conexao.sqlProtection(comentarios.getTitulo())+","
            +Conexao.sqlProtection(comentarios.getDescricao())+","
            +Conexao.sqlProtection(comentarios.getData_CadastroStr("yyyy-MM-dd HH-mm-ss"))+","
            +comentarios.getExibirStr()+""
            +comentarios.getid_menuStr()+""
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
    private comentariosBEAN alterar(comentariosBEAN comentarios) throws Exception{
        String SQL=" update comentarios set "
        +"titulo=" +Conexao.sqlProtection(comentarios.getTitulo())+"," 
        +"descricao="+Conexao.sqlProtection(comentarios.getDescricao())+","
        +"data_cadastro="+Conexao.sqlProtection(comentarios.getData_CadastroStr("yyyy-MM-dd HH-mm-ss"))+","
        +"exibir="+comentarios.getExibirStr()+","
        +"id_menu="+comentarios.getid_menuStr()+" "
        +" where(id="+comentarios.getID()+")";
        try{
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){
            throw new Exception("Erro ao salvar Comentario.");
        }   
        return buscarID(comentarios);
    }
    public comentariosBEAN excluir(comentariosBEAN comentarios)throws Exception{
        String SQL="delete from comentarios where(id="+comentarios.getID()+")";
        try{
            comentarios = buscarID(comentarios);
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            return comentarios;
        }
        catch(Exception erro){
            throw new Exception("Erro ao excluir Comentario.");
        }
    }
    public comentariosBEAN buscarID(comentariosBEAN Comentario)throws Excecao{
        comentariosBEAN acc = new comentariosBEAN();
        try{
            String SQL = SELECT+" where(comentarios.id=0"+Comentario.getIDStr()+");";                   
            acc=(comentariosBEAN)buscar(SQL).get(0); 
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
                    comentariosPOJO POJO = new  comentariosPOJO();
                    POJO.setID(MinhaConexao.MostrarCampoInteger("id"));
                    POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));
                    POJO.setDescricao(MinhaConexao.MostrarCampoStr("descricao"));
                    POJO.setData_Cadastro(MinhaConexao.MostrarCampoDate("data_cadastro"));
                    POJO.setExibir(MinhaConexao.MostrarCampoBoolean("exibir"));
                    POJO.setid_menu(MinhaConexao.MostrarCampoInteger("id_menu"));
                    comentariosBEAN Comentarios_BEAN = new comentariosBEAN(POJO);
                    registros.add(Comentarios_BEAN);
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
     public Vector buscarUltimos()throws Excecao{
       try{ 
         return buscar(SELECT+"  order by comentarios.id desc ");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
     public Vector buscarUltimos(int quantidade)throws Excecao{
       try{ 
         return buscar(SELECT+" order by comentarios.id desc limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    
     public Vector buscarUltimosAtivos(int quantidade,menuBEAN menu)throws Excecao{
       try{ 
         return buscar(SELECT+" where(exibir=true) and (menu.id="+menu.getIDStr()+")  order by comentarios.id desc limit 0 ,0"+quantidade);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
     public Vector buscarPorMenu(menuBEAN menu)throws Excecao{
       try{ 
         return buscar(SELECT+" where(menu.id="+menu.getIDStr()+")");
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
     
    public Vector buscartitulo(String titulo)throws Excecao{
       try{ 
         String SQL=SELECT+"  where(comentarios.titulo like'%"+titulo.replaceAll("'","''") +"%');";
         
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    public Vector buscar(comentariosBEAN comentarios)throws Excecao{
        try{
        String SQL=SELECT;
        String condicao="";
        if (comentarios.getTitulo()!=null){
            if (condicao.length()>0)
                condicao+="and";
            condicao+="(comentarios.titulo like'%"+comentarios.getTitulo().replaceAll("'","''") +"%')";
        }
        if(comentarios.getDescricao()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(comentarios.descricao like'%"+comentarios.getDescricao().replaceAll("'","''") +"%')";
        }
        if(comentarios.getData_Cadastro()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(comentarios.data_cadastro ="+Conexao.sqlProtection(comentarios.getData_CadastroStr("yyyy-MM-dd"))+")";
        }
        if(comentarios.getExibirStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(comentarios.exibir ="+comentarios.getExibirStr()+")";
         }         
        if(comentarios.getid_menuStr()!=null){
             if (condicao.length()>0)
                condicao+="and";
             condicao+="(comentarios.id_menu ="+comentarios.getid_menuStr()+")";
         }         
         SQL=(condicao.length()>0)?SQL+" where "+condicao:SQL; 
         return buscar(SQL);
       }
       catch(Exception erro){
           throw new Excecao("Erro ao buscar Comentario! ERRO:");
       }
     }
    
    
    
    public comentariosBEAN buscarUltimo(){
       comentariosBEAN acc = new comentariosBEAN();
        try{ 
         String SQL=SELECT+"  order by comentarios.id desc";
         
         return ((comentariosBEAN)buscar(SQL).get(0));
       }
       catch(Exception erro){
           return acc;
       }
     }
}
