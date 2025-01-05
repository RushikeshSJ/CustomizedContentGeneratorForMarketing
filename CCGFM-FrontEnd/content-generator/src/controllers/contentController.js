import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/content/generate'; // Replace with actual API

export const generateContent = async (formData) => {
  try {
    const requestData = {
      tone: formData.tone,
      targetAudience: formData.targetAudience || 'general',
      format: formData.format,
      length: formData.length,
      // Add any other required fields
    };
    const response = await axios.post(API_URL, requestData);
    return response.data.generatedContent;
  } catch (error) {
    console.error('Error generating content:', error);
    return 'An error occurred. Please try again.';
  }
};

