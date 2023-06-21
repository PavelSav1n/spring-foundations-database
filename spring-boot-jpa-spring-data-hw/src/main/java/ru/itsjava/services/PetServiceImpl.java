package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itsjava.domain.Pet;
import ru.itsjava.repository.PetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository; // @Autowired through constructor (@RA)

    @Transactional
    @Override
    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    @Transactional(readOnly = true)
    @Override
    public Pet findById(long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public void printAll() {
        for (Pet pet : findAll()) {
            System.out.println(pet);
        }
    }

    @Transactional
    @Override
    public Pet update(Pet pet) {
        return petRepository.save(pet);
    }

    @Transactional
    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }
}
