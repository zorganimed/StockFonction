package com.ste.mzo.rest;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ste.mzo.entities.Provider;
import com.ste.mzo.repositories.ProviderRepository;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping({ "/providers", "/hom" })
public class ProviderRestController {

	@Autowired
	private ProviderRepository providerRepository;

	@GetMapping("/list")
	public List<Provider> listProviders() {
		List<Provider> lp = (List<Provider>) providerRepository.findAll();
		return lp;
	}

	@GetMapping("/{providerId}")
	public Optional<Provider> getProvider(@PathVariable Long providerId) {
		return providerRepository.findById(providerId);

	}

	@PostMapping("/add")
	public void addProvider(@Valid @RequestBody Provider provider) {

		providerRepository.save(provider);
	}
	
	@PutMapping("/{providerId}")
	public Provider updateProvider(@PathVariable Long providerId,@Valid @RequestBody Provider providerRequest) {
		return providerRepository.findById(providerId).map(
				provider->{
					provider.setName(providerRequest.getName());
					provider.setEmail(providerRequest.getEmail());
					provider.setAdress(providerRequest.getAdress());
					return providerRepository.save(provider);
				}
				).orElseThrow(()-> new ResourceNotFoundException("ProviderId "+providerId+" not found "));
	}
	
	@DeleteMapping("/{providerId}")
	public ResponseEntity<?> deleteProvider(@PathVariable Long providerId)
	{
		return providerRepository.findById(providerId).map(
				provider->{
					providerRepository.delete(provider);
					return ResponseEntity.ok().build();
				}
				).orElseThrow(()-> new ResourceNotFoundException("ProviderId "+providerId+" not found "));
	}}
