package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.transaction.Transactional;
import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.model.MemoireRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoireService {

    private final MemoireRepository memoireRepository;

    public MemoireService (final MemoireRepository memoireRepository)
    {
        this.memoireRepository = memoireRepository;
    }

    public List<Memoire> getAllMemoire()
    {
        return this.memoireRepository.findAll();
    }

    public Optional<Memoire> getMemoireById(final int id)
    {
        return this.memoireRepository.findById(id); //.orElseThrow(() -> new IllegalStateException("Cet memoire n'existe pas"));
    }

    @Transactional
    public Memoire addMemoire(final Memoire memoire)
    {
        return this.memoireRepository.save(memoire);
    }

    @Transactional
    public Memoire deleteMemoire(final Integer id){
        Memoire memoire = memoireRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet memoire n'existe pas"));
        memoireRepository.delete(memoire);
        return memoire;
    }


    @Transactional
    public Memoire updateMemoire(final Integer id, final Memoire memoire){
        Memoire memoireToUpdate = memoireRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet memoire n'existe pas"));

        if (memoireToUpdate != null) {
            BeanUtils.copyProperties(memoire, memoireToUpdate, "id");
            memoireRepository.save(memoireToUpdate);
        }
        return memoireToUpdate;
    }

}
