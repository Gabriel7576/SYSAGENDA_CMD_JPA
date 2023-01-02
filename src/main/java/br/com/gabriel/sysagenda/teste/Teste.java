package br.com.gabriel.sysagenda.teste;

import java.util.List;

import br.com.gabriel.sysagenda.dao.LigacaoDao;
import br.com.gabriel.sysagenda.domain.Ligacao;

public class Teste {

	public static void main(String[] args) throws Exception {

		LigacaoDao dao = new LigacaoDao();

//		Ligacao lig = dao.getLigacao(1, 1);
//		System.out.println(lig);

		System.out.println("-------------------------");

		List<Ligacao> ligacoes = dao.lista();
		for (Ligacao ligacao : ligacoes) {
			System.out.println(ligacao);
		}
		
//		dao.deletaTodas(1);

	}

}
