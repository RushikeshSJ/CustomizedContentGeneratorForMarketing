import React, { useState } from 'react';
import Navbar from './components/Navbar';
import ContentForm from './components/ContentForm';
import GeneratedContent from './components/GeneratedContent';
import './styles/components.css';

function App() {
  const [content, setContent] = useState('');

  return (
    <div>
      <Navbar />
      <ContentForm setContent={setContent} />
      <GeneratedContent content={content} />
    </div>
  );
}

export default App;
