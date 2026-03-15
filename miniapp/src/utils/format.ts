export const formatPrice = (price: number | string): string => {
  const num = typeof price === 'string' ? parseFloat(price) : price;
  return `¥${num.toFixed(0)}`;
};

export const formatDate = (dateStr: string): string => {
  if (!dateStr) return '';
  return dateStr.replace(/-/g, '/').substring(0, 10);
};

export const formatDateTime = (dateStr: string): string => {
  if (!dateStr) return '';
  return dateStr.substring(0, 16).replace('T', ' ');
};

export const getDaysBetween = (start: string, end: string): number => {
  const s = new Date(start);
  const e = new Date(end);
  return Math.ceil((e.getTime() - s.getTime()) / (1000 * 60 * 60 * 24));
};

export const getMonthsBetween = (start: string, end: string): number => {
  const s = new Date(start);
  const e = new Date(end);
  return (e.getFullYear() - s.getFullYear()) * 12 + (e.getMonth() - s.getMonth());
};
