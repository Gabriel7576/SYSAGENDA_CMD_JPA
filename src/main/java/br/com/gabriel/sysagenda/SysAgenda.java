package br.com.gabriel.sysagenda;

import java.util.List;
import java.util.Scanner;

import br.com.gabriel.sysagenda.business.ContatoBss;
import br.com.gabriel.sysagenda.business.LigacaoBss;
import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.domain.LigacaoId;
import br.com.gabriel.sysagenda.util.Funcoes;
import br.com.gabriel.sysagenda.util.Titulo;

public class SysAgenda {

	private static int opcao;
	private static ContatoBss contatoBss = new ContatoBss();
	private static LigacaoBss ligacaoBss = new LigacaoBss();

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(System.in);

			do {
				System.out.println("-------------------------------------");
				System.out.println("1 - MENU CONTATO");
				System.out.println("2 - MENU LIGAÇÃO");
				System.out.println("3 - SAIR");
				System.out.println("-------------------------------------");

				System.out.print("Digite a opção aqui : ");
				opcao = sc.nextInt();

				switch (opcao) {
				case 1:
					mostraMenuContato(sc);
					break;
				case 2:
					mostraMenuLigacao(sc);
					break;
				case 3:
					break;
				default:
					System.out.println("\nLeia as opções por favor!!");
					break;
				}
			} while (opcao != 3);

			sc.close();
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void mostraMenuContato(Scanner sc) {

		boolean sn;
		do {
			System.out.println("\n-------------------------------------");
			System.out.println("1 - LISTAR CONTATOS");
			System.out.println("2 - ADICIONA CONTATO");
			System.out.println("3 - ALTERAR CONTATO");
			System.out.println("4 - APAGAR CONTATO");
			System.out.println("5 - VOLTAR");
			System.out.println("-------------------------------------" + "\n");

			String op;
			do {
				System.out.print("Digite a opção aqui : ");
				op = sc.next();
				sn = Funcoes.verificaNumero(op);
			} while (sn);

			opcao = Integer.parseInt(op);

			switch (opcao) {

			case 1:
				// LISTA
				List<Contato> contatolista = contatoBss.getLista();

				System.out.println("-------------------------------------");

				System.out.print("|         Nome           ");
				System.out.print("| Cod Contato  ");
				System.out.print("|Telefone");
				System.out.println();

				for (Contato contato2 : contatolista) {
					System.out.print("|");
					System.out.print(String.format("%-24.24s", contato2.getNome()));
					System.out.print("|");
					System.out.print(String.format("%-14.14s", contato2.getCodContato()));
					System.out.print("|");
					System.out.print(contato2.getTelefone());
					System.out.println();
				}

				System.out.println("-------------------------------------");

				break;

			case 2:
				// ADICIONA
				Contato contatoAdiciona = new Contato();
				contatoAdiciona.setCodContato(contatoBss.getCodContato() + 1);

				do {

					System.out.print("\nDIGITE O NOME DO CONTATO : ");
					String nome = sc.next();
					sn = Funcoes.verificaNome(nome);

					if (sn) {
						System.out.println("\nPOR FAVOR DIGITAR SO LETRAS!!\n");
					} else {
						contatoAdiciona.setNome(nome);
					}

				} while (sn);

				do {

					System.out.print("\nDIGITE O TELEFONE DO CONTATO : ");
					String num = sc.next();
					sn = Funcoes.verificaNumero(num);

					if (sn) {
						System.out.println("\nPOR FAVOR DIGITAR SO NÚMEROS!!\n");
					} else {
						contatoAdiciona.setTelefone(Long.parseLong(num));
					}

				} while (sn);

				contatoBss.adiciona(contatoAdiciona);

				System.out.println("\nCONTATO ADICIONADO\n");
				break;

			case 3:
				// ALTERA
				System.out.print("\nDIGITE O " + Titulo.COD_CONTATO + " A SER ALTERADO : ");
				Contato contatoAltera = contatoBss.getContato(sc.nextInt());

				if (contatoAltera == null) {

					System.out.print("\nESTE CONTATO NÃO EXISTE!!");

				} else {

					System.out.print("\nNOME [" + contatoAltera.getNome() + "] : ");

					sc.nextLine();
					String nom = sc.nextLine();

					if (nom != null & !nom.equals("")) {
						contatoAltera.setNome(nom);
					}

					System.out.print("\nTELEFONE [" + contatoAltera.getTelefone() + "] : ");

					String num = sc.nextLine();

					if (!num.equals("")) {
						contatoAltera.setTelefone(Long.parseLong(num));
					}

					contatoBss.alteraContato(contatoAltera);

					System.out.println("\nContato alterado com Sucesso");
				}
				break;

			case 4:
				// DELETA
				System.out.print("\nDigite o " + Titulo.COD_CONTATO + " do contato para deleta-lo : ");
				Integer codContato = sc.nextInt();

				Contato contato = contatoBss.getContato(codContato);

				if (contato == null) {

					System.out.println("\nO Contato não existe!!");

				} else {
					sc.nextLine();

					System.out.print("\nEssa ação podera apagar as ligações desse contato. Deseja Continuar [s/n] : ");

					String resp = sc.nextLine();

					if (resp.equalsIgnoreCase("s") || resp.equalsIgnoreCase("sim")) {
						contatoBss.deletaContato(contato);
						System.out.println("\nExcluido com Sucesso!\n");
					}
				}

				break;

			default:
				break;
			}

			if (opcao > 5 && opcao <= 0) {
				System.out.println("\nVocê leu as opções??\n");
			}

		} while (opcao != 5);
	}

	public static void mostraMenuLigacao(Scanner sc) {

		String op;
		boolean sn;
		String cod;
		String obs = null;

		do {
			System.out.println("-------------------------------------");
			System.out.println("1 - LISTAR LIGAÇÕES");
			System.out.println("2 - ADICIONAR LIGAÇÃO");
			System.out.println("3 - ALTERAR LIGAÇÃO");
			System.out.println("4 - Apagar LIGAÇÃO");
			System.out.println("5 - voltar");
			System.out.println("-------------------------------------");

			do {
				System.out.print("Digite a opção aqui : ");
				op = sc.next();
				sn = Funcoes.verificaNumero(op);
			} while (sn == true);

			System.out.println();
			opcao = Integer.parseInt(op);

			switch (opcao) {
			case 1:
				// LISTA
				List<Ligacao> list = ligacaoBss.getlista();

				System.out.println("-------------------------------------\n");

				// Tituloa
				System.out.print("|         Nome           ");
				System.out.print("| Cod ligacao ");
				System.out.print("|    Data/Hora       ");
				System.out.print("|Obs                ");
				System.out.println();

				// Ligacoes
				for (Ligacao ligacao : list) {
					Contato contato = contatoBss.getContato(ligacao.getId().getCodContato());
					String codNome = ligacao.getId().getCodContato() + "-" + contato.getNome();
					System.out.print("|");
					System.out.print(String.format("%-24.24s", codNome));
					System.out.print("| ");
					System.out.print(String.format("%-12.12s", ligacao.getId().getCodLigacao() + ""));
					System.out.print("|");
					System.out.print(String.format("%-20.20s", Funcoes.dateToStr(ligacao.getDataHora())));
					System.out.print("|");
					System.out.print(ligacao.getObs());
					System.out.println();
				}

				System.out.println("-------------------------------------");

				break;

			case 2:
				// ADICIONA
				Ligacao ligacaoAdiciona = new Ligacao();
				ligacaoAdiciona.setId(new LigacaoId());

				do {
					System.out.print("Digite o " + Titulo.COD_CONTATO + " : ");
					cod = sc.next();
					sn = Funcoes.verificaNumero(cod);
				} while (sn);

				ligacaoAdiciona.getId().setCodContato(Integer.parseInt(cod));

				if (contatoBss.getContato(Integer.parseInt(cod)) == null) {

					System.out.println("\n" + Titulo.COD_CONTATO + " não existe!!");

				} else {

					ligacaoAdiciona.getId().setCodLigacao(ligacaoBss.getCodLigacao(Integer.parseInt(cod)) + 1);

					String data = null;
					String hora = null;
					do {

						System.out.print("Digite a data [dia/mes/ano] : ");
						data = sc.next();

						String[] dt = Funcoes.separa(data, "/");

						int num = Integer.parseInt(dt[1]);
						if (num >= 13) {
							sn = false;
						}

					} while (sn);

					do {
						System.out.print("Digite a Hora [HH:MM] : ");
						hora = sc.next();
						String[] horas = Funcoes.separa(hora, ":");
						Integer h = Integer.parseInt(horas[0]);
						Integer m = Integer.parseInt(horas[1]);

						if (horas.length < 3) {

							if (h >= 24 | m >= 60) {

								System.out.println("\nHoras ou Minutos acima do Limite");
								sn = true;

							} else {
								sn = false;
							}
						}

					} while (sn);

					ligacaoAdiciona.setDataHora(Funcoes.strToDate(data + " " + hora));

					System.out.print("Digite a observação {opcional} : ");

					sc.nextLine();
					obs = sc.nextLine();
					ligacaoAdiciona.setObs(obs);

					ligacaoBss.adicionaLigacao(ligacaoAdiciona);

					System.out.println("\nLigação adicionada");
				}

				break;

			case 3:
				// ALTERA
				LigacaoId id = new LigacaoId();

				do {
					System.out.print("Digite o " + Titulo.COD_CONTATO + " para o qual você ligou : ");
					cod = sc.next();
					sn = Funcoes.verificaNumero(cod);
				} while (sn);

				id.setCodContato(Integer.parseInt(cod));

				// Verifica se o contato existe
				Contato contato1 = contatoBss.getContato(Integer.parseInt(cod));
				if (contato1 == null) {
					System.out.println("\n" + Titulo.COD_CONTATO + " não existe!!");
				} else {
					do {
						System.out.print("Digite o " + Titulo.COD_LIGACAO + " a ser alterada : ");
						cod = sc.next();
						sn = Funcoes.verificaNumero(cod);
					} while (sn);

					id.setCodLigacao(Integer.parseInt(cod));

					// Verifica se a ligação existe
					Ligacao ligacaoAltera = ligacaoBss.getLigacao(id);
					if (ligacaoAltera == null) {
						System.out.println("\nLigação não existe!");
					} else {
						System.out.print("Digite a data [dia/mes/ano] : ");
						sc.nextLine();

						String data = null;
						String hora = null;
						data = sc.nextLine();
						do {
							System.out.print("Digite a Hora [HH:MM] : ");

							hora = sc.nextLine();
							if (hora != null & !hora.equals("")) {
								String[] horas = Funcoes.separa(hora, ":");
								Integer h = Integer.parseInt(horas[0]);
								Integer m = Integer.parseInt(horas[1]);

								if (h >= 24 | m >= 60) {
									System.out.println("\nHoras ou Minutos acima do Limite");
									sn = true;
								} else {
									sn = false;
								}
							}
						} while (sn);

						ligacaoAltera.setDataHora(Funcoes.strToDate("'" + data + " " + hora + "'"));

						System.out.print("Digite a observação : ");
						obs = sc.nextLine();

						if (obs != null & !obs.equals("")) {
							ligacaoAltera.setObs(obs);
						}

						ligacaoBss.alteraLigacao(ligacaoAltera);

						System.out.println();
					}
				}
				break;

			case 4:
				// DELETA
				LigacaoId idDel = new LigacaoId();
				sc.nextLine();

				System.out.print("Digite o " + Titulo.COD_CONTATO + " : ");
				idDel.setCodContato(Integer.parseInt(sc.nextLine()));
				System.out.print("Digite o " + Titulo.COD_LIGACAO + " a ser deletado : ");
				idDel.setCodLigacao(Integer.parseInt(sc.nextLine()));

				Ligacao ligacaoApaga = ligacaoBss.getLigacao(idDel);

				if (ligacaoApaga == null) {
					System.out.println("\nEsse Ligação não existe!!");
				} else {
					ligacaoBss.deletaLigacao(ligacaoApaga);
				}

				break;

			default:
				break;
			}

		} while (opcao != 5);
	}

}
