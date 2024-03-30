package service;

import model.Situation;

public interface FichierService {

    Situation lecture(String pathName);

    void ecriture(Situation situation, String filePath);
}
