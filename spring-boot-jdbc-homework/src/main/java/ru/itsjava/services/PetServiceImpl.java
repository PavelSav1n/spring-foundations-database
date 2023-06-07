package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itsjava.dao.PetDao;
import ru.itsjava.domain.Pet;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetDao petDao;

    @Override
    public int count() {
        return petDao.count();
    }

    @Override
    public List<Pet> findAll() {
        return petDao.findAll();
    }

    @Override
    public Pet insert(Pet pet) {
        return petDao.insert(pet);
    }

    @Override
    public void updateById(Pet pet, long id) {
        petDao.updateById(pet, id);
    }

    @Override
    public Pet delete(long id) {
        return petDao.delete(id);
    }

    @Override
    public Pet findById(long id) {
        return petDao.findById(id);
    }
}
