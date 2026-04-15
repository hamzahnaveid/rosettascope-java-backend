package dto;

import java.util.Map;

public class ConfidenceUpdateRequests {
	private String email;
    private Map<String, Double> confidenceScores;

    public ConfidenceUpdateRequests() {
    	
    }

    public String getEmail() {
    	return email; 
    }

    public Map<String, Double> getConfidenceScores() {
    	return confidenceScores; 
    }

    public void setEmail(String email) {
    	this.email = email; 
    }

    public void setConfidenceScores(Map<String, Double>confidenceScores) {
    	this.confidenceScores = confidenceScores; 
    }

}
