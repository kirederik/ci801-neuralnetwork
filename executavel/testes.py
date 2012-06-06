# -*- coding: cp1252 -*-
import os

numexec = 10
bases = [0,1,2,3,4]
opmut = [ -1, 0, 1, 2, 3, 4, 5]
opcros = [ -1, 0, 1, 2, 3 ]
populacao = [50, 100]
elites = 10
torneio = [2, 4]
geracoes = [ 500 ]
probmut = 0.1
probcross = 1.0

basestr = ["Cancer", "Diabates", "Glass", "Heart", "Iris"]

def criaString(base, opmut, opcro, popsize, elites,torneio, ger, probm, probc):
    outfile = "b" + str(base) + "-" + \
              "ger" + str(ger) + "-" + \
              "opm" + str(opmut) + "-" + \
              "opc" + str(opcro) + "-" + \
              "pop" + str(popsize) + "-" + \
              "eli" + str(elites) + "-" + \
              "tor" + str(torneio) + "-" + \
              "prm" + str(probm) + "-" + \
              "prc" + str(probc)
    return outfile;



def executa(base, opmut, opcro, popsize, elites,torneio, ger, probm, probc):
    basestr = "java -jar RNAGA.jar"
    paramstr = " " + str(base) + \
               " " + str(opmut) + \
               " " + str(opcro) + \
               " " + str(popsize) + \
               " " + str(elites) + \
               " " + str(torneio) + \
               " " + str(ger) + \
               " " + str(probm) + \
               " " + str(probc)
    execstr = basestr + paramstr + " > tmp.txt"
    os.system(execstr)
    entrada = open("tmp.txt")
    linhas = entrada.readlines()
    return linhas


def test():
    executa(0, -1, -1, 20, 4, 4, 50, 0.1, 0.9)

def rodartestes():
    for base in bases:

        saida = open("o" + basestr[base] + ".csv", "w")
        
        for p in populacao:
            for t in torneio:
                for om in opmut:
                    for oc in opcros:
                        for g in geracoes:
						
                            arquivo = criaString(base, om, oc, p, elites, t, g, probmut, probcross)
                            saida.write("" + arquivo + "\n")
                            print arquivo
							
                            for x in range(numexec):
								out = executa(base, om, oc, p, elites, t, g, probmut, probcross)
								saida.write("Execução " + str(x+1) + ";")
								saida.write("" + out[1].strip() + ";")
								saida.write("" + out[2].strip() + ";")
								saida.write("" + out[3].strip() + ";\n")

if __name__ == '__main__': 
	rodartestes()
	