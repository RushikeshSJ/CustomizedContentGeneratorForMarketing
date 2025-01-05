import React, { useState } from 'react';
import { generateContent } from '../controllers/contentController';
import '../styles/components.css';

const ContentForm = ({ setContent }) => {
  const [formData, setFormData] = useState({
    tone: '',
    targetAudience: 'general',
    format: '',
    length: 'short',
    keyword: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.keyword || !formData.tone || !formData.format) {  // Check if all required fields are filled
      alert('Please fill out all required fields.');
      return;
    }
    const generatedData = await generateContent(formData);
    setContent(generatedData);
  };
  

  return (
    <form onSubmit={handleSubmit} className="content-form">
      <label>
        Keyword:
        <input
          type="text"
          name="keyword"
          value={formData.keyword}
          onChange={handleChange}
          required
        />
      </label>
      <label>
        Tone:
        <select name="tone" value={formData.tone} onChange={handleChange}>
          <option value="">Select Tone</option>
          <option value="friendly">Friendly</option>
          <option value="professional">Professional</option>
          <option value="funny">Funny</option>
        </select>
      </label>
      <label>
        Length:
        <select name="length" value={formData.length} onChange={handleChange}>
          <option value="short">Short</option>
          <option value="medium">Medium</option>
          <option value="long">Long</option>
        </select>
      </label>
      <label>
        Format:
        <select name="format" value={formData.format} onChange={handleChange} required>
          <option value="">Select Format</option>
          <option value="text">Text</option>
          <option value="article">Article</option>
          <option value="email">Email</option>
        </select>
      </label>
      <button type="submit">Generate Content</button>
    </form>
  );
};

export default ContentForm;
